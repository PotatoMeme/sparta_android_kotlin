package com.potatomeme.searchapp.data.model

import com.potatomeme.searchapp.data.model.Item

data class SampleItem(
    override val imgUrl: String,
    override val title: String,
    override val date: String,
    override val link: String ="",
    override val isImage: Boolean = true,
    override var isFavorite: Boolean = false
) : Item
