package com.tsoft.taskcase.repo

import com.tsoft.taskcase.utils.Resource

interface ImageRepository {
    suspend fun getImages(page:Int): Resource
}