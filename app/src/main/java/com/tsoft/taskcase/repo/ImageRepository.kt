package com.tsoft.taskcase.repo

import androidx.paging.Pager
import com.tsoft.taskcase.model.ImageHit

interface ImageRepository {
    fun getImagesPager(): Pager<Int, ImageHit>
    suspend fun addFavorite(imageHit: ImageHit)
    suspend fun deleteFavoriteById(id: Int)
    suspend fun getFavoriteList(): List<ImageHit>
    suspend fun getFavoriteIds(): List<Int>
}