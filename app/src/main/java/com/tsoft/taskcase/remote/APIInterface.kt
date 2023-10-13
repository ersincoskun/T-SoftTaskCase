package com.tsoft.taskcase.remote

import com.tsoft.taskcase.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("api/")
    suspend fun getYellowFlowers(
        @Query("key") apiKey: String = "40023657-46b4452747ccb2b7fd4b3e2f9",
        @Query("q") query: String = "yellow+flowers",
        @Query("image_type") imageType: String = "photo",
        @Query("page") page: Int
    ): Response<ImageResponse?>?
}