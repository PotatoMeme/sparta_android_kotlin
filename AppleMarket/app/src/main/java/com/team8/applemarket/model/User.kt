package com.team8.applemarket.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int, // 고유 키값
    val name: String, // 이름
    val address: String, // 주소
    val menorTemperature: Double,// 메너온도
    val imgRes: Int, // 이미지 리소스
) : Parcelable