package com.potatomeme.hotelreservationprogram

import android.os.Build
import androidx.annotation.RequiresApi
import com.potatomeme.hotelreservationprogram.model.LogCharge
import com.potatomeme.hotelreservationprogram.model.Reservation
import com.potatomeme.hotelreservationprogram.service.Service
import com.potatomeme.hotelreservationprogram.util.ShowRule

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val service = Service.getInstance()
    val rule = ShowRule()
    println("호텔 예약 프로그램입니다.")
    while (true) {
        when (rule.showMenuSelect()) {
            Key.MENU_RESERVATION -> {
                val userName: String = rule.showSelectName(Key.MENU_RESERVATION)
                val roomNum: Int = rule.showSelectRoomName()
                val checkInDate: String
                while (true){
                    val currentCheckInDate = rule.showSelectCheckInDate()
                    if (service.checkCheckInDate(roomNum,currentCheckInDate)){
                        checkInDate = currentCheckInDate
                        break
                    }
                    System.err.println("해당 날짜에 이미 방을 사용중입니다. 다른 날짜를 입력해주세요")
                }
                val checkOutDate: String
                while (true){
                    val currentCheckOutDate = rule.showSelectCheckOutDate(checkInDate)
                    if (service.checkCheckOutDate(roomNum,checkInDate,currentCheckOutDate)){
                        checkOutDate = currentCheckOutDate
                        break
                    }
                    System.err.println("해당 날짜에 이미 방을 사용중입니다. 다른 날짜를 입력해주세요")
                }
                service.addReservation(Reservation(userName, roomNum, checkInDate, checkOutDate))
                service.addCharge(
                    LogCharge(
                        userName,
                        "초기 금액으로 ${(100000..200000).random()} 원 입금되었습니다."
                    )
                )
                service.addCharge(
                    LogCharge(
                        userName,
                        "예약금으로 ${(100000..200000).random()} 원 출금되었습니다."
                    )
                )
                println("호텔 예약이 완료되었습니다.")
            }

            Key.MENU_PRINT_RESERVATION_LIST -> {
                println("호텔 예약자 목록입니다.")
                print(service.getReservationListStr())
            }

            Key.MENU_PRINT_RESERVATION_SORTED_LIST -> {
                println("호텔 예약자 목록입니다. (정렬완료)")
                print(service.getSortedReservationListStr())
            }

            Key.MENU_EXIT -> break
            Key.MENU_PRINT_CHARGE_HISTORY -> {
                val userName = rule.showSelectName(Key.MENU_PRINT_CHARGE_HISTORY)
                print(service.getUserChargeLog(userName))
            }
            Key.MENU_RESERVATION_MODIFY -> {}
        }
    }
    println("호텔 예약 프로그램을 종료 합니다.")
}



