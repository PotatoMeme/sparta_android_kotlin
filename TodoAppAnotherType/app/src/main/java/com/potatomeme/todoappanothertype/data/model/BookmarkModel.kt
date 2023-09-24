package com.potatomeme.todoappanothertype.data.model

data class BookmarkModel(
    val id: Long,
    val title: String?,
    val description: String?,
    val isBookmark: Boolean = false
)

fun BookmarkModel.toTodoModel(): TodoModel {
    return TodoModel(
        id = id,
        title = title,
        description = description,
        isBookmark = isBookmark
    )
}