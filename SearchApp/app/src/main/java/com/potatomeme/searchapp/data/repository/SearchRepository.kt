package com.potatomeme.searchapp.data.repository

import com.potatomeme.searchapp.data.model.Item
import com.potatomeme.searchapp.data.model.image.ImageResponse
import com.potatomeme.searchapp.data.model.video.VideoResponse
import retrofit2.Response

interface SearchRepository {
    suspend fun searchImages(
        query: String,
    ): Response<ImageResponse>

    suspend fun searchVideos(
        query: String,
    ): Response<VideoResponse>

    fun getListItem(): List<Item>
    fun saveListItem(list: List<Item>)
}