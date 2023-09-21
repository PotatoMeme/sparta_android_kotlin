package com.potatomeme.searchapp.data.model.video

data class VideoResponse(
    val documents: List<VideoDocument>,
    val meta: Meta
)