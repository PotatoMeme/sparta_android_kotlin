package com.potatomeme.todoappanothertype.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.potatomeme.todoappanothertype.data.model.TodoModel
import com.potatomeme.todoappanothertype.data.repository.TodoRepository
import com.potatomeme.todoappanothertype.data.repository.TodoRepositoryImpl

class TodoViewModel(
    //private val idGenerate: AtomicLong
    private val todoRepository: TodoRepository // to be
) : ViewModel() {

    private val _list: MutableLiveData<List<TodoModel>> = MutableLiveData()
    val list: LiveData<List<TodoModel>> get() = _list

    init {
        _list.value = todoRepository.getTestData()
    }

    /**
     * TodoModel 아이템을 추가합니다.
     */
    fun addTodoItem(
        item: TodoModel?
    ) {
        _list.value = todoRepository.addTodoItem(item)
    }

    fun modifyTodoItem(
        item: TodoModel?,
    ) {
        _list.value = todoRepository.modifyTodoItem(item)
    }

    fun modifyTodoItemWithPos(
        position: Int?,
        item: TodoModel?,
    ) {
        _list.value = todoRepository.modifyTodoItemWithPos(position,item)
    }

    fun removeTodoItem(position: Int?) {
        _list.value = todoRepository.removeTodoItem(position)
    }
}

class TodoViewModelFactory : ViewModelProvider.Factory {

    // id 를 부여할 값
    private val todoRepository : TodoRepository = TodoRepositoryImpl()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(todoRepository) as T
        } else {
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}