package com.potatomeme.searchapp.data.api

import com.potatomeme.searchapp.data.model.image.ImageResponse
import com.potatomeme.searchapp.data.model.video.VideoResponse
import com.potatomeme.searchapp.util.Constant.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchApi {
    @Headers("Authorization: KakaoAK $API_KEY")
    @GET("v2/search/image")
    suspend fun searchImages(
        @Query("query") query: String,
    ) : Response<ImageResponse>

    @Headers("Authorization: KakaoAK $API_KEY")
    @GET("v2/search/vclip")
    suspend fun searchVideos(
        @Query("query") query: String,
    ) : Response<VideoResponse>
}