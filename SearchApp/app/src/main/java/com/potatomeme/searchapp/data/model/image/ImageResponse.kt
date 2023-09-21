package com.potatomeme.searchapp.data.model.image

data class ImageResponse(
    val documents: List<ImageDocument>,
    val meta: Meta
)