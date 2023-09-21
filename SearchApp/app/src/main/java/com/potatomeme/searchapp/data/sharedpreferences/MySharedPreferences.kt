package com.potatomeme.searchapp.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.potatomeme.searchapp.R
import com.potatomeme.searchapp.data.model.Item
import com.potatomeme.searchapp.data.model.SampleItem

class MySharedPreferences(context: Context) {
    companion object {
        private const val KEY = "favorite_list"
    }

    private val mySharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    fun getListPefs(): List<Item> {
        val stringPrefs = mySharedPreferences.getString(KEY, null)
        return if (stringPrefs != null && stringPrefs != "[]") {
            GsonBuilder().create().fromJson(
                stringPrefs,
                object : TypeToken<ArrayList<SampleItem>>() {}.type
            )
        } else listOf()
    }

    fun saveListPrefs(list: List<Item>){
        val arrayList = arrayListOf<Item>().apply {
            addAll(list)
        }
        val mEditPrefs = mySharedPreferences.edit()
        val stringPrefs = GsonBuilder().create().toJson(
            arrayList,
            object : TypeToken<ArrayList<SampleItem>>() {}.type
        )
        mEditPrefs.putString(KEY,stringPrefs).apply()
    }
}