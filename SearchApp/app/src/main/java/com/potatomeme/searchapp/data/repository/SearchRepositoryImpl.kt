package com.potatomeme.searchapp.data.repository

import com.potatomeme.searchapp.data.api.RetrofitInstance.api
import com.potatomeme.searchapp.data.model.image.ImageResponse
import com.potatomeme.searchapp.data.model.video.VideoResponse
import retrofit2.Response

class SearchRepositoryImpl : SearchRepository {
    override suspend fun searchImages(query: String): Response<ImageResponse> {
        return api.searchImages(query)
    }

    override suspend fun searchVideos(query: String): Response<VideoResponse> {
        return api.searchVideos(query)
    }
}