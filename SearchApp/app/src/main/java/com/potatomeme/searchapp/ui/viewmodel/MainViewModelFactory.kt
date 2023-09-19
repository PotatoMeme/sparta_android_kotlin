package com.potatomeme.searchapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.potatomeme.searchapp.data.repository.SearchRepository

class MainViewModelFactory(private val searchRepository: SearchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(searchRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")

    }
}