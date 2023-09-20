package com.potatomeme.searchapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SampleItem(
    override val imgUrl: String,
    override val title: String,
    override val date: String,
    override val link: String = "",
    override val isImage: Boolean = true,
    override var isFavorite: Boolean = false,
) : Item, Parcelable

fun Item.toSampleItem(): SampleItem {
    return SampleItem(
        imgUrl, title, date, link, isImage, isFavorite
    )
}