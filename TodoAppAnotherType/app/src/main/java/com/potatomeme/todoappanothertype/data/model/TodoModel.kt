package com.potatomeme.todoappanothertype.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoModel(
    val id: Long? = -1,
    val title: String?,
    val description: String?,
    val isBookmark: Boolean = false
) : Parcelable

fun TodoModel.toBookmarkModel(): BookmarkModel {
    return BookmarkModel(
        id = id ?: 0,
        title = title,
        description = description,
        isBookmark = isBookmark
    )
}