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
    val logChargeList : List<LogCharge>
        get() = _logChargeList


    fun addReservation(reservation: Reservation) {
        _reservationList.add(reservation)
    }

    fun addCharge(logCharge: LogCharge){
        _logChargeList.add(logCharge)
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