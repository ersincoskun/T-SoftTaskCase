package com.tsoft.taskcase.remote

import com.tsoft.taskcase.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("api/")
    suspend fun getYellowFlowers(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("image_type") imageType: String,
        @Query("page") page: Int
    ): ImageResponse
}