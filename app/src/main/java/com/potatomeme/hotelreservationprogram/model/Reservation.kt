package com.potatomeme.hotelreservationprogram.model

data class Reservation(
    val userName: String,
    val roomNumber: Int,
    val checkInDate: String,//"YYYYMMDD"
    val checkOutDate: String,//"YYYYMMDD"
) {

    override fun toString(): String {
        return "사용자 : $userName, 방번호 : ${roomNumber}호, 체크인 : ${checkInDate.getDate()}, 체크아웃 : ${checkOutDate.getDate()}"
    }

    private fun String.getDate() = "${substring(0, 4)}-${substring(4, 6)}-${substring(6)}"
}