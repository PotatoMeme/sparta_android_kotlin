package com.potatomeme.todoappanothertype.data.repository

import com.potatomeme.todoappanothertype.data.model.TodoModel

interface TodoRepository {
    fun getTestData(): List<TodoModel>
    fun addTodoItem(item: TodoModel?) : List<TodoModel>
    fun modifyTodoItem(item: TodoModel?): List<TodoModel>
    fun modifyTodoItemWithPos(position: Int?, item: TodoModel?): List<TodoModel>
    fun removeTodoItem(position: Int?): List<TodoModel>
}