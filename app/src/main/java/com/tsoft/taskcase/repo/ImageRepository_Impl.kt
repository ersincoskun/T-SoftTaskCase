package com.tsoft.taskcase.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tsoft.taskcase.ui.ImagePagingSource
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.remote.APIInterface
import javax.inject.Inject

class ImageRepository_Impl @Inject constructor(val retrofitAPI: APIInterface) : ImageRepository {
    override fun getImagesPager(): Pager<Int, ImageHit> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { ImagePagingSource(retrofitAPI) }
        )
    }
}