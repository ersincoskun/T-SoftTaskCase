package com.tsoft.taskcase.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tsoft.taskcase.base.BaseFragment
import com.tsoft.taskcase.databinding.FragmentImageListBinding
import com.tsoft.taskcase.ui.ImageAdapter
import com.tsoft.taskcase.viewmodel.ImageListFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageListFragment : BaseFragment<FragmentImageListBinding>() {

    private val viewModel: ImageListFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ImageAdapter()
        binding.rvImageList.adapter = adapter
        viewModel.getImageList()
        viewModel.imagesLiveData.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                adapter.submitData(pagingData)
            }
        }
    }

}