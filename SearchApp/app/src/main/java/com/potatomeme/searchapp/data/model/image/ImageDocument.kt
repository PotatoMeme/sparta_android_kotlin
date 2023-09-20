package com.potatomeme.searchapp.data.model.image

import com.google.gson.annotations.SerializedName
import com.potatomeme.searchapp.data.model.Item

data class ImageDocument(
    val collection: String,
    val display_sitename: String,
    @SerializedName("doc_url")
    override val link: String,
    val height: Int,
    @SerializedName("image_url")
    override val imgUrl: String,
    val thumbnail_url: String,
    val width: Int,
    @SerializedName("datetime")
    override val date: String,
) : Item {
    override val title : String
        get() = "[$collection] $display_sitename"
    override val isImage: Boolean
        get() = true
    override var isFavorite: Boolean = false
}