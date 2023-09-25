package com.potatomeme.todoappanothertype.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.potatomeme.todoappanothertype.data.model.BookmarkModel
import com.potatomeme.todoappanothertype.data.model.TodoModel
import com.potatomeme.todoappanothertype.data.model.toBookmarkModel
import com.potatomeme.todoappanothertype.data.model.toTodoModel

class MainSharedViewModel : ViewModel() {
    private val _todoEvent: MutableLiveData<MainSharedEventForTodo> = MutableLiveData()
    val todoEvent: LiveData<MainSharedEventForTodo> get() = _todoEvent

    private val _bookmarkEvent: MutableLiveData<MainSharedEventForBookmark> = MutableLiveData()
    val bookmarkEvent: LiveData<MainSharedEventForBookmark> get() = _bookmarkEvent

    fun updateBookmarkItems(items: List<TodoModel>?) {
        items?.map {
            it.toBookmarkModel()
        }?.filter {
            it.isBookmark
        }?.also {
            _bookmarkEvent.value = MainSharedEventForBookmark.UpdateBookmarkItems(it)
        }
    }

    fun updateTodoItem(item: BookmarkModel) {
        _todoEvent.value = MainSharedEventForTodo.UpdateTodoItem(item.toTodoModel())
    }
}

sealed interface MainSharedEventForTodo {

    data class UpdateTodoItem(
        val item: TodoModel
    ) : MainSharedEventForTodo
}


sealed interface MainSharedEventForBookmark {

    data class UpdateBookmarkItems(
        val items: List<BookmarkModel>
    ) : MainSharedEventForBookmark
}