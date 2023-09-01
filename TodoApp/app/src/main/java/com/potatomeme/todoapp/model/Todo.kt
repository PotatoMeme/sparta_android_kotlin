package com.potatomeme.todoapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Todo(
    val title: String,
    val description: String,
    val id: Int = idIncrease++,
) : Parcelable{
    companion object{
        private var idIncrease = 0
    }
}
