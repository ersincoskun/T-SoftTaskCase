package com.tsoft.taskcase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.repo.ImageRepository
import com.tsoft.taskcase.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel
@Inject
constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _isFavoriteLiveData = SingleLiveEvent<Boolean>()
    val isFavoriteLiveData: LiveData<Boolean>
        get() = _isFavoriteLiveData


    fun checkIsFavorite(imageHit: ImageHit) {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteIds = imageRepository.getFavoriteIds()
            val isFavorite=favoriteIds.contains(imageHit.id)
            withContext(Dispatchers.Main) {
                _isFavoriteLiveData.value = isFavorite
            }
        }
    }

    fun addFavorite(imageHit: ImageHit) {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteIds = imageRepository.getFavoriteIds()
            if (!favoriteIds.contains(imageHit.id)) {
                imageRepository.addFavorite(imageHit)
            }
        }
    }

    fun deleteFavoriteById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.deleteFavoriteById(id)
        }
    }


}