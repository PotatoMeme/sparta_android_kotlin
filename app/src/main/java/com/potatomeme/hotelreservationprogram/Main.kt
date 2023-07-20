package com.potatomeme.hotelreservationprogram

import android.os.Build
import androidx.annotation.RequiresApi
import com.potatomeme.hotelreservationprogram.model.LogCharge
import com.potatomeme.hotelreservationprogram.model.Reservation
import com.potatomeme.hotelreservationprogram.service.Service
import com.potatomeme.hotelreservationprogram.util.ShowRule

@RequiresApi(Build.VERSION_CODES.O)
fun main(){
    val service = Service.getInstance()
    val rule = ShowRule()
    println("호텔 예약 프로그램입니다.")
    while (true){
        when(rule.showMenuSelect()){
            Key.MENU_RESERVATION -> {
                val userName : String = rule.showSelectName()
                val roomNum : Int = rule.showSelectRoomName()
                val checkInDate : String = rule.showSelectCheckInDate()
                val checkOutDate : String = rule.showSelectCheckOutDate(checkInDate)
                service.addReservation(Reservation(userName,roomNum,checkInDate,checkOutDate))
                service.addCharge(LogCharge(userName,"초기 금액으로 ${(100000 .. 200000).random()} 원 입금되었습니다."))
                service.addCharge(LogCharge(userName,"예약금으로 ${(100000 .. 200000).random()} 원 출금되었습니다."))
            }
            Key.MENU_PRINT_RESERVATION_LIST ->{}
            Key.MENU_PRINT_RESERVATION_SORTED_LIST ->{}
            Key.MENU_EXIT -> break
            Key.MENU_PRINT_CHARGE_HISTORY->{}
            Key.MENU_RESERVATION_MODIFY->{}
        }
    }
    println("호텔 예약 프로그램을 종료 합니다.")
}



