package com.tsoft.taskcase.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tsoft.taskcase.model.ImageHit
import com.tsoft.taskcase.remote.APIInterface
import retrofit2.HttpException

class ImagePagingSource(
    private val retrofitAPI: APIInterface
) : PagingSource<Int, ImageHit>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageHit> {
        val position = params.key ?: 1
        return try {
            val response = retrofitAPI.getYellowFlowers(page = position)
            val images = response?.body()?.hits
            if(images != null && response.isSuccessful) {
                LoadResult.Page(
                    data = images,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (images.isEmpty()) null else position + 1
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ImageHit>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

