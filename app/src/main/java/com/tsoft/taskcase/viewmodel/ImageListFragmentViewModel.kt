package com.tsoft.taskcase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.repo.ImageRepository
import com.tsoft.taskcase.utils.SingleLiveEvent
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

    private val _filteredImagesLiveData = SingleLiveEvent<PagingData<ImageHit>>()
    val filteredImagesLiveData: LiveData<PagingData<ImageHit>> = _filteredImagesLiveData

    fun filterImages(query: String) {
        val filtered = imagesLiveData.value?.filter {
            it.user.contains(query, ignoreCase = true) // 'user' alanını filtreleme kriteri olarak kullanıyoruz
        }
        filtered?.let {
            _filteredImagesLiveData.postValue(it)
        }
    }

    fun getImageList() {
        viewModelScope.launch {
            _imagesLiveData = imageRepository.getImagesPager()
                .flow
                .cachedIn(viewModelScope)
                .asLiveData()
        }
    }

}