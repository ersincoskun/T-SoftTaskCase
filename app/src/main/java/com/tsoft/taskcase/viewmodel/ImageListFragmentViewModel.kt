package com.tsoft.taskcase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.repo.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListFragmentViewModel
@Inject
constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private lateinit var _imagesLiveData: LiveData<PagingData<ImageHit>>
    val imagesLiveData: LiveData<PagingData<ImageHit>>
        get() = _imagesLiveData

    fun getImageList() {
        viewModelScope.launch {
            _imagesLiveData = imageRepository.getImagesPager()
                .flow
                .cachedIn(viewModelScope)
                .asLiveData()
        }
    }

}