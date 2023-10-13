package com.tsoft.taskcase.repo

import com.tsoft.taskcase.remote.APIInterface
import com.tsoft.taskcase.utils.Resource
import com.tsoft.taskcase.utils.printErrorLog
import javax.inject.Inject

class ImageRepository_Impl @Inject constructor(val retrofitAPI: APIInterface) : ImageRepository {

    override suspend fun getImages(page: Int): Resource {
        val response = retrofitAPI.getYellowFlowers(page = page)
        response?.let { safeResponse ->
            if (safeResponse.isSuccessful) {
                safeResponse.body()?.let { safeResponseBody ->
                    printErrorLog("response body: $safeResponseBody")
                    return Resource.Success(safeResponseBody)
                } ?: run {
                    printErrorLog("null body")
                    return Resource.Error("RESPONSE_BODY_NULL")
                }
            } else {
                printErrorLog("response not successful")
                return Resource.Error("RESPONSE_NOT_SUCCESSFUL")
            }
        } ?: run {
            printErrorLog("response null")
            return Resource.Error("RESPONSE_NULL")
        }
    }

}