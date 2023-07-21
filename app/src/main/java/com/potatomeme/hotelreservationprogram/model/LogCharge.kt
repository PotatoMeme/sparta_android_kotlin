package com.potatomeme.hotelreservationprogram.model

import com.potatomeme.hotelreservationprogram.Key

data class LogCharge(
    val userName : String,
    val logState : Int,
    val charge : Int,
){
    override fun toString(): String {
        return when(logState){
            Key.CHARGE_FIRST -> "초기 금액으로 $charge 원 입금되었습니다"
            Key.CHARGE_DEPOSIT -> "예약금으로 $charge 원 출금되었습니다"
            Key.CHARGE_REFUND_CANCEL -> "예약취소로 $charge 원 환불되었습니다"
            Key.CHARGE_REFUND_MODIFY -> "예약수정으로 $charge 원 환불되었습니다"
            else -> throw IllegalStateException("logState($logState) is over the state range")
        }
    }
}
