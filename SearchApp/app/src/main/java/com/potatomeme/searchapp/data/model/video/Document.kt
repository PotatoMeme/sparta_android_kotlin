package com.potatomeme.searchapp.data.model.video

import com.google.gson.annotations.SerializedName
import com.potatomeme.searchapp.data.model.Item

data class Document(
    val author: String,
    @SerializedName("datetime")
    override val date: String,
    val play_time: Int,
    @SerializedName("thumbnail")
    override val imgUrl: String,
    override val title: String,
    @SerializedName("url")
    override val link: String
): Item