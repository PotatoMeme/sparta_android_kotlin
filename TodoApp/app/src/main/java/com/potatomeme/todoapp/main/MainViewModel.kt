package com.potatomeme.todoapp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.potatomeme.todoapp.model.Todo

class MainViewModel : ViewModel() {
    private val list = mutableListOf<Todo>()

    private val _todoList: MutableLiveData<List<Todo>> = MutableLiveData()
    val todoList: LiveData<List<Todo>> get() = _todoList

    init {
        _todoList.value = list
    }


    fun insertTodo(todo: Todo) {
        list.add(todo)
        _todoList.value = list
    }

    // with id
    fun removeTodoWithId(id: Int) {
        val currentPosition = list.indexOfFirst { it.id == id }
        list.removeAt(currentPosition)
        _todoList.value = list
    }

    fun changedTodo(todo: Todo) {
        val currentPosition = list.indexOfFirst { it.id == todo.id }
        list[currentPosition] = todo
        _todoList.value = list
    }

    fun changedTagWithId(id: Int) {
        val currentPosition = list.indexOfFirst { it.id == id }
        val changedItem =
            list[currentPosition].copy(favoriteTag = !list[currentPosition].favoriteTag)
        list[currentPosition] = changedItem
        _todoList.value = list
    }

    // with pos
    fun removeTodoWithPos(pos: Int) {
        list.removeAt(pos)
        _todoList.value = list
    }

    fun changedTagWithPos(pos: Int) {
        val changedItem = list[pos].copy(favoriteTag = !list[pos].favoriteTag)
        list[pos] = changedItem
        _todoList.value = list
    }
}