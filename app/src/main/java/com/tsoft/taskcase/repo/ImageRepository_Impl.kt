package com.tsoft.taskcase.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.remote.APIInterface
import com.tsoft.taskcase.storage.dao.ImageHitDao
import com.tsoft.taskcase.ui.ImagePagingSource
import javax.inject.Inject

class ImageRepository_Impl @Inject constructor(
    val retrofitAPI: APIInterface,
    val imageHitDao: ImageHitDao
) : ImageRepository {
    override fun getImagesPager(): Pager<Int, ImageHit> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { ImagePagingSource(retrofitAPI) }
        )
    }

    override suspend fun addFavorite(imageHit: ImageHit) {
        imageHitDao.addFavorite(imageHit)
    }

    override suspend fun deleteFavoriteById(id: Int) {
        imageHitDao.deleteFavoriteById(id)
    }

    override suspend fun getFavoriteList(): List<ImageHit> {
        return imageHitDao.getFavoriteList()
    }

    override suspend fun getFavoriteIds(): List<Int> {
        return imageHitDao.getFavoriteIds()
    }
}