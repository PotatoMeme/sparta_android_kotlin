package com.team8.applemarket.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val id: Int, // 고유 키값
    val name: String, // 이름
    val description: String, //설명
    val price: Int, // 가격
    val imgRes: Int, // 이미지 리소스
    val userId: Int, // 유저 키값
    val talkCount: Int, // 톡 카운트
    val favoriteCount: Int, // 좋아요 카운트
    val favoriteFlag: Boolean = false, // 좋아요 플래그
) : Parcelable
