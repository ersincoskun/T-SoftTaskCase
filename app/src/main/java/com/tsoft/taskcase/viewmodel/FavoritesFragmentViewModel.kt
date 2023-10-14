package com.tsoft.taskcase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
class FavoritesFragmentViewModel
@Inject
constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _favoritesListLiveData = SingleLiveEvent<List<ImageHit>>()
    val favoritesListLiveData: LiveData<List<ImageHit>>
        get() = _favoritesListLiveData


    fun getFavoritesList() {
        viewModelScope.launch(Dispatchers.IO) {
            val favoritesList = imageRepository.getFavoriteList()
            withContext(Dispatchers.Main) {
                _favoritesListLiveData.value = favoritesList
            }
        }
    }

    fun deleteFavoriteById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.deleteFavoriteById(id)
        }
    }


}