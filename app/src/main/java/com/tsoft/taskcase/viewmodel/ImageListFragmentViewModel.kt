package com.tsoft.taskcase.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.repo.ImageRepository
import com.tsoft.taskcase.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListFragmentViewModel
@Inject
constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _imagesLiveData = MutableLiveData<PagingData<ImageHit>>()
    val imagesLiveData: LiveData<PagingData<ImageHit>>
        get() = _imagesLiveData

    private val _filteredImagesLiveData = MutableLiveData<PagingData<ImageHit>>()
    val filteredImagesLiveData: LiveData<PagingData<ImageHit>> = _filteredImagesLiveData

    private val _imageLiveDataReadyCallback = SingleLiveEvent<Boolean>()
    val imageLiveDataReadyCallback: LiveData<Boolean> = _imageLiveDataReadyCallback

    private var currentQuery = ""
    var isImagesLiveDataListenEnable = true

    fun getImageList() {
        viewModelScope.launch {
            val favoriteIds = imageRepository.getFavoriteIds()
            val newFlow = imageRepository.getImagesPager().flow
                .map { pagingData ->
                    pagingData.map { imageHit ->
                        imageHit.copy(isFavorite = favoriteIds.contains(imageHit.id))
                    }
                }
                .cachedIn(viewModelScope)

            newFlow.asLiveData().observeForever { newData ->
                _imagesLiveData.value = newData
            }

            _imageLiveDataReadyCallback.value = true
        }
    }

    fun filterImages(query: String) {
        currentQuery = query
        isImagesLiveDataListenEnable = false
        val filtered = imagesLiveData.value?.filter {
            it.user.contains(query, ignoreCase = true)
        }
        filtered?.let {
            _filteredImagesLiveData.postValue(it)
        }
    }

    fun addFavorite(imageHit: ImageHit) {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.addFavorite(imageHit)
            refreshImageList()
        }
    }

    fun deleteFavoriteById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.deleteFavoriteById(id)
            refreshImageList()
        }
    }

    private fun refreshImageList() {
        viewModelScope.launch {
            val favoriteIds = imageRepository.getFavoriteIds()
            val currentPagingData = _imagesLiveData.value ?: return@launch

            val newPagingData = currentPagingData.map { imageHit ->
                imageHit.copy(isFavorite = favoriteIds.contains(imageHit.id))
            }

            _imagesLiveData.postValue(newPagingData)
            if (_filteredImagesLiveData.value != null) {
                filterImages(currentQuery)
            }

        }
    }

}