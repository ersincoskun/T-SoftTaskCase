package com.tsoft.taskcase.repo

import androidx.paging.Pager
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.utils.Resource

interface ImageRepository {
    fun getImagesPager(): Pager<Int, ImageHit>
}