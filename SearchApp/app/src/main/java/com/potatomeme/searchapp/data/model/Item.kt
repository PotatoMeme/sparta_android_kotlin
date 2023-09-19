package com.potatomeme.searchapp.data.model

interface Item {
    val imgUrl : String
    val title : String
    val date : String
    val link : String
    val isImage : Boolean
    var isFavorite : Boolean
}