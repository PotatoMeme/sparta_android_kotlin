package com.potatomeme.todoappanothertype.data.repository

import com.potatomeme.todoappanothertype.data.model.TodoModel
import java.util.concurrent.atomic.AtomicLong

class TodoRepositoryImpl : TodoRepository {
    private val idGenerate: AtomicLong = AtomicLong(1L)
    private val list = ArrayList<TodoModel>()

    override fun getTestData(): List<TodoModel> {
        return list.apply {
            for (i in 0 until 3) {
                add(
                    TodoModel(
                        idGenerate.getAndIncrement(),
                        "title $i",
                        "description $i"
                    )
                )
            }
        }.toList()
    }

    override fun addTodoItem(item: TodoModel?): List<TodoModel> {
        requireNotNull(item) { "add todo item funcition : todo is null" }
        list.add(
            item.copy(
                id = idGenerate.getAndIncrement()
            )
        )
        return list.toList()
    }

    override fun modifyTodoItem(item: TodoModel?): List<TodoModel> {
        requireNotNull(item) { " modify todo item function : todo is null" }
        val findPosition = list.indexOfFirst { it.id == item.id }
        require(findPosition >= 0) { " modify todo item function : todo is not in list" }
        list[findPosition] = item
        return list.toList()
    }

    override fun modifyTodoItemWithPos(position: Int?, item: TodoModel?): List<TodoModel> {
        requireNotNull(position) { " modify todo item with position function : position is null" }
        require(position >= 0) { " modify todo item with position function : position is under 0 pos" }
        requireNotNull(item) { " modify todo item with position function : todo is null" }
        list[position] = item
        return list.toList()
    }

    override fun removeTodoItem(position: Int?): List<TodoModel> {
        requireNotNull(position) { " remove todo item with position function : position is null" }
        require(position >= 0) { " remove todo item with position function : position is under 0" }
        list.removeAt(position)
        return list.toList()
    }
}