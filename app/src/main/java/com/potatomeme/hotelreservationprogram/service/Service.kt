package com.potatomeme.hotelreservationprogram.service

import com.potatomeme.hotelreservationprogram.model.LogCharge
import com.potatomeme.hotelreservationprogram.model.Reservation

class Service private constructor() {
    //예약리스트
    private val _reservationList: MutableList<Reservation> = mutableListOf()
    val reservationList: List<Reservation>
        get() = _reservationList

    //금액 * 입금 출금내역 목록
    private val _logChargeList: MutableList<LogCharge> = mutableListOf()
    val logChargeList: List<LogCharge>
        get() = _logChargeList


    fun addReservation(reservation: Reservation) {
        _reservationList.add(reservation)
    }

    fun addCharge(logCharge: LogCharge) {
        _logChargeList.add(logCharge)
    }

    fun checkCheckInDate(roomNum: Int, currentCheckInDate: String): Boolean {
        return reservationList.all { reservation ->
            reservation.roomNumber != roomNum || ((reservation.checkInDate > currentCheckInDate) || (reservation.checkOutDate <= currentCheckInDate))
        }
    }

    fun checkCheckOutDate(
        roomNum: Int,
        currentCheckInDate: String,
        currentCheckOutDate: String,
    ): Boolean {
        return reservationList.all { reservation ->
            reservation.roomNumber != roomNum || ((reservation.checkInDate > currentCheckInDate && reservation.checkInDate >= currentCheckOutDate) || (reservation.checkOutDate <= currentCheckInDate))
        }
    }

    fun getReservationListStr(): String = buildString {
        reservationList.forEachIndexed { index, reservation ->
            append(index + 1).append(". ").appendLine(reservation.toString())
        }
    }

    fun getSortedReservationListStr(): String = buildString {
        reservationList.sortedBy { it.userName }.forEachIndexed { index, reservation ->
            append(index + 1).append(". ").appendLine(reservation.toString())
        }
    }

    fun getUserChargeLog(userName: String): String = buildString {
        logChargeList.filter { it.userName == userName }.let { list ->
            if (list.isEmpty()) {
                appendLine("예약된 사용자를 찾을수 없습니다.")
            } else {
                list.forEachIndexed { index, logCharge ->
                    append(index + 1).append(". ").appendLine(logCharge.log)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var instance: Service? = null

        fun getInstance(): Service {
            if (instance == null) {
                synchronized(this) {
                    instance = Service()
                }
            }
            return instance!!
        }
    }
}