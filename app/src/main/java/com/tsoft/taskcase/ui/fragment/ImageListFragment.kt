package com.tsoft.taskcase.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tsoft.taskcase.base.BaseFragment
import com.tsoft.taskcase.databinding.FragmentImageListBinding
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.ui.ImageAdapter
import com.tsoft.taskcase.viewmodel.ImageListFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageListFragment : BaseFragment<FragmentImageListBinding>() {

    private val viewModel: ImageListFragmentViewModel by viewModels()
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ImageAdapter(addFavoriteAction = { imageHit ->
            viewModel.addFavorite(imageHit)
        }, deleteFavoriteAction = { id ->
            viewModel.deleteFavoriteById(id)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvImageList.adapter = adapter
        viewModel.getImageList()
        subLiveData()
        setListeners()
    }

    private fun setListeners() {
        binding.etListSearch.addTextChangedListener { editable ->
            editable?.toString()?.let { query ->
                viewModel.filterImages(query)
            }
        }
    }

    private fun subLiveData() {
        viewModel.imageLiveDataReadyCallback.observe(viewLifecycleOwner){
            viewModel.imagesLiveData.observe(viewLifecycleOwner) { pagingData ->
                lifecycleScope.launch {
                    adapter.submitData(pagingData)
                    //adapter.notifyDataSetChanged()
                }
            }
        }

        viewModel.filteredImagesLiveData.observe(viewLifecycleOwner) { pagingData ->
            pagingData?.let {
                lifecycleScope.launch {
                    adapter.submitData(it)
                }
            }
        }
    }

}