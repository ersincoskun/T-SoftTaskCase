package com.tsoft.taskcase.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.tsoft.taskcase.base.BaseFragment
import com.tsoft.taskcase.databinding.FragmentImageListBinding
import com.tsoft.taskcase.ui.ImageAdapter
import com.tsoft.taskcase.viewmodel.ImageListFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageListFragment : BaseFragment<FragmentImageListBinding>() {

    private val viewModel: ImageListFragmentViewModel by viewModels()
    private lateinit var adapter: ImageAdapter

    //sayfaya tekrar girildiğinde LoadStateListener en son listenin stateini döndürüyor
    //son state'i almamak için bu değişken kullanılıyor
    private var isCameFromSubmit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ImageAdapter(itemClickAction = { imageHit ->
            val navDirections = ImageListFragmentDirections.actionImageListFragmentToDetailFragment(imageHit)
            navigate(navDirections = navDirections)
        }, addFavoriteAction = { imageHit ->
            viewModel.addFavorite(imageHit)
        }, deleteFavoriteAction = { id ->
            viewModel.deleteFavoriteById(id)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavView()
        binding.rvImageList.adapter = adapter
        showProgressBar()
        viewModel.getImageList()
        subLiveData()
        setListeners()
    }

    private fun setListeners() {
        binding.etListSearch.addTextChangedListener { editable ->
            editable?.toString()?.let { query ->
                if (query.isNotEmpty()) viewModel.filterImages(query)
            }
        }

        adapter.addLoadStateListener { loadState ->
            // submitData asenkron bir işlem olduğu için bu işlem tamamlandığında progressbar'ı kapatıyoruz
            if (loadState.refresh is LoadState.NotLoading && isCameFromSubmit) {
                hideProgressBar()
                isCameFromSubmit = false
            }
        }
    }

    private fun subLiveData() {
        viewModel.imageLiveDataReadyCallback.observe(viewLifecycleOwner){
            viewModel.imagesLiveData.observe(viewLifecycleOwner) { pagingData ->
                if (viewModel.isImagesLiveDataListenEnable) {
                    lifecycleScope.launch {
                        isCameFromSubmit = true
                        adapter.submitData(pagingData)
                    }
                }
            }
        }

        viewModel.filteredImagesLiveData.observe(viewLifecycleOwner) { pagingData ->
            pagingData?.let {
                lifecycleScope.launch {
                    isCameFromSubmit = true
                    adapter.submitData(it)
                }
            }
        }
    }

}