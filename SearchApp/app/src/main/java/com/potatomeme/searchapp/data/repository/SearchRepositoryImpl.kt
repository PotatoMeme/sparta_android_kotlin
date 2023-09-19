package com.potatomeme.searchapp.data.repository

import com.potatomeme.searchapp.data.api.RetrofitInstance.api
import com.potatomeme.searchapp.data.model.Item
import com.potatomeme.searchapp.data.model.image.ImageResponse
import com.potatomeme.searchapp.data.model.video.VideoResponse
import com.potatomeme.searchapp.data.sharedpreferences.MySharedPreferences
import retrofit2.Response

class SearchRepositoryImpl(private val mySharedPreferences: MySharedPreferences) : SearchRepository {
    override suspend fun searchImages(query: String): Response<ImageResponse> {
        return api.searchImages(query)
    }

    override suspend fun searchVideos(query: String): Response<VideoResponse> {
        return api.searchVideos(query)
    }

    override fun getListItem(): List<Item> {
        return mySharedPreferences.getListPefs()
    }

    override fun saveListItem(list: List<Item>){
        mySharedPreferences.saveListPrefs(list)
    }
}