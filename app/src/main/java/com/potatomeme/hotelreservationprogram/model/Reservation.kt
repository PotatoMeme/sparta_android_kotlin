package com.potatomeme.hotelreservationprogram.model

import com.potatomeme.hotelreservationprogram.Key

data class Reservation(
    val userName: String,
    val roomNumber: Int,
    val checkInDate: String,//"YYYYMMDD"
    val checkOutDate: String,//"YYYYMMDD"
    val firstCharge: Int,
    val depositCharge: Int,
) {

    fun toString(type: Int): String {
        return when (type) {
            Key.MENU_PRINT_RESERVATION_LIST, Key.MENU_PRINT_RESERVATION_SORTED_LIST -> "사용자 : $userName, 방번호 : ${roomNumber}호, 체크인 : ${checkInDate.getDate()}, 체크아웃 : ${checkOutDate.getDate()}"
            Key.MENU_RESERVATION_MODIFY -> "방번호 : ${roomNumber}호, 체크인 : ${checkInDate.getDate()}, 체크아웃 : ${checkOutDate.getDate()}"
            else -> throw IllegalArgumentException("toString type($type) is over the range")
        }

    }

    private fun String.getDate() = "${substring(0, 4)}-${substring(4, 6)}-${substring(6)}"
}