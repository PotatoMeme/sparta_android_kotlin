package com.potatomeme.hotelreservationprogram.model

data class Reservation(
    val userName: String,
    val roomNumber: Int,
    val checkInDate: String,//"YYYYMMDD"
    val checkOutDate: String,//"YYYYMMDD"
)