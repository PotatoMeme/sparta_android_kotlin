package com.potatomeme.searchapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomeme.searchapp.data.model.Item
import com.potatomeme.searchapp.data.repository.SearchRepository
import com.potatomeme.searchapp.data.repository.SearchRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _itemList: MutableLiveData<List<Item>> = MutableLiveData()
    val itemList: LiveData<List<Item>>
        get() = _itemList
    val searchRepository: SearchRepository = SearchRepositoryImpl()

    fun searchApi(query: String) = viewModelScope.launch(Dispatchers.IO) {
        val arrayList: ArrayList<Item> = arrayListOf()

        val imageResponse = searchRepository.searchImages(query)
        val videoResponse = searchRepository.searchVideos(query)

        if (imageResponse.isSuccessful) {
            imageResponse.body()?.let { body ->
                arrayList.addAll(body.documents)
            }
        }else{
            Log.d(TAG, "searchApi: imageResponse.isNotSuccessful")
            Log.d(TAG, imageResponse.message())
        }

        if (videoResponse.isSuccessful) {
            videoResponse.body()?.let { body ->
                arrayList.addAll(body.documents)
            }
        }else{
            Log.d(TAG, "searchApi: videoResponse.isNotSuccessful")
            Log.d(TAG, imageResponse.message())
        }

        arrayList.sortBy { it.date }
        //Log.d(TAG, "searchApi: ${_itemList.value?.size} ${arrayList.size}")
        _itemList.postValue(arrayList)
        //Log.d(TAG, "searchApi: ${_itemList.value?.size}")
    }

}