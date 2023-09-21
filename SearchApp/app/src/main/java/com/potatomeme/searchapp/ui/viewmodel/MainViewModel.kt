package com.potatomeme.searchapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomeme.searchapp.data.model.Item
import com.potatomeme.searchapp.data.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val searchRepository: SearchRepository) : ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _itemList: MutableLiveData<List<Item>> = MutableLiveData()
    val itemList: LiveData<List<Item>>
        get() = _itemList


    private val _favoriteItemList: MutableLiveData<List<Item>> = MutableLiveData()
    val favoriteItemList: LiveData<List<Item>>
        get() = _favoriteItemList


    init {
        _favoriteItemList.value = searchRepository.getListItem()
    }

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

        arrayList.sortByDescending { it.date }

        for (index in arrayList.indices){
            arrayList[index].isFavorite = favoriteItemList.value.orEmpty().any { it.imgUrl == arrayList[index].imgUrl }
        }

        //Log.d(TAG, "searchApi: ${_itemList.value?.size} ${arrayList.size}")
        _itemList.postValue(arrayList)
        //Log.d(TAG, "searchApi: ${_itemList.value?.size}")
    }

    private fun changeItemInSeachList(item : Item){
        val currentList = itemList.value.orEmpty().toMutableList()
        val changedItemIndex = currentList.indexOfFirst { it.imgUrl == item.imgUrl }
        if (changedItemIndex != -1){
            currentList[changedItemIndex] = item
            _itemList.value = currentList
        }
    }

    fun addFavoriteItem(item: Item){
        Log.d(TAG, "addFavoriteItem: $item")
        val currentList = favoriteItemList.value.orEmpty().toMutableList()
        currentList.add(item)
        _favoriteItemList.value = currentList
        changeItemInSeachList(item)
        searchRepository.saveListItem(favoriteItemList.value.orEmpty())
    }

    fun removeFavoriteItem(item: Item){
        val currentList = favoriteItemList.value.orEmpty().toMutableList()
        val removeIndex = currentList.indexOfFirst { it.imgUrl == item.imgUrl }
        if (removeIndex != -1){
            currentList.removeAt(removeIndex)
            _favoriteItemList.value = currentList
            changeItemInSeachList(item)
            searchRepository.saveListItem(favoriteItemList.value.orEmpty())
        }
    }

    fun addFavoriteItemInWebView(item: Item){
        Log.d(TAG, "addFavoriteItem: $item")
        val currentList = favoriteItemList.value.orEmpty().toMutableList()
        currentList.add(item)
        _favoriteItemList.value = currentList
        changeItemInSeachList(item)
    }

    fun removeFavoriteItemInWebView(item: Item){
        val currentList = favoriteItemList.value.orEmpty().toMutableList()
        val removeIndex = currentList.indexOfFirst { it.imgUrl == item.imgUrl }
        if (removeIndex != -1){
            currentList.removeAt(removeIndex)
            _favoriteItemList.value = currentList
            changeItemInSeachList(item)
        }
    }

}