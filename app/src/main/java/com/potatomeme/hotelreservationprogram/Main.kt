package com.potatomeme.hotelreservationprogram

import android.os.Build
import androidx.annotation.RequiresApi
import com.potatomeme.hotelreservationprogram.model.LogCharge
import com.potatomeme.hotelreservationprogram.model.Reservation
import com.potatomeme.hotelreservationprogram.service.Service
import com.potatomeme.hotelreservationprogram.util.Utils.calculateRefundRate
import com.potatomeme.hotelreservationprogram.util.Validation

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val validation = Validation()
    println("호텔 예약 프로그램입니다.")
    while (true) {
        when (validation.showMenuSelect()) {
            Key.MENU_RESERVATION -> handleReservation(validation)
            Key.MENU_PRINT_RESERVATION_LIST -> showReservationList()
            Key.MENU_PRINT_RESERVATION_SORTED_LIST -> showSoretedReservationList()
            Key.MENU_EXIT -> break
            Key.MENU_PRINT_CHARGE_HISTORY -> showChargeHistory(validation)
            Key.MENU_RESERVATION_MODIFY -> modifyReservation(validation)
        }
    }
    println("호텔 예약 프로그램을 종료 합니다.")
}

@RequiresApi(Build.VERSION_CODES.O)
fun handleReservation(validation: Validation) {
    val userName: String = validation.showSelectName(Key.MENU_RESERVATION)
    val roomNum: Int = validation.showSelectRoomName()
    val checkInDate: String = validation.showSelectCheckInDate(roomNum)
    val checkOutDate: String = validation.showSelectCheckOutDate(roomNum, checkInDate)
    val firstCharge = (100000..200000).random()
    val depositCharge = (50000..firstCharge).random()

    Service.getInstance().addReservation(
        Reservation(
            userName,
            roomNum,
            checkInDate,
            checkOutDate,
            firstCharge,
            depositCharge
        )
    )
    Service.getInstance().addCharge(LogCharge(userName, Key.CHARGE_FIRST, firstCharge))
    Service.getInstance().addCharge(LogCharge(userName, Key.CHARGE_DEPOSIT, depositCharge))
    println("호텔 예약이 완료되었습니다.")
}

fun showReservationList() {
    println("호텔 예약자 목록입니다.")
    print(Service.getInstance().getReservationListStr())
}

fun showSoretedReservationList() {
    println("호텔 예약자 목록입니다. (정렬완료)")
    print(Service.getInstance().getSortedReservationListStr())
}

fun showChargeHistory(validation: Validation) {
    val userName: String = validation.showSelectName(Key.MENU_PRINT_CHARGE_HISTORY)
    print(Service.getInstance().getUserChargeLog(userName))
}

@RequiresApi(Build.VERSION_CODES.O)
fun modifyReservation(validation: Validation) {
    val userName: String = validation.showSelectName(Key.MENU_PRINT_CHARGE_HISTORY)
    val reservationList: List<Pair<Int, String>> =
        Service.getInstance().getUserReservationListWithIndex(userName)
    if (reservationList.isEmpty()) {
        println("사용자 이름으로 예약된 목록을 찾을 수 없습니다. ")
    } else {
        val reservationStr = buildString {
            reservationList.forEachIndexed { index, pair ->
                append(index + 1).append(". ").appendLine(pair.second)
            }
        }
        println("$userName 님이 예약한 목록입니다. 변경하실 예약번호를 입력해주세요(탈출을 exit 입니다.)")
        val selectedNum: Int
        while (true) {
            print(reservationStr)
            val line = readLine()!!
            if (line == "exit") return
            if (line.toIntOrNull() != null && line.toInt() in 1..reservationList.size) {
                selectedNum = line.toInt() - 1
                val currentReservationIdx: Int = reservationList[selectedNum].first
                val beforeReservation: Reservation =
                    Service.getInstance().reservationList[currentReservationIdx]
                println("해당 예약을 어떻게 하시겠습니까? 1. 변경 2. 취소 / 이외번호. 메뉴로 돌아가기")
                when (readLine()!!.toIntOrNull()) {
                    Key.RESERVATION_MODIFY -> {
                        val roomNum: Int = validation.showSelectRoomName()
                        val checkInDate: String =
                            validation.showSelectCheckInDate(roomNum, currentReservationIdx)
                        val checkOutDate: String = validation.showSelectCheckOutDate(
                            roomNum,
                            checkInDate,
                            currentReservationIdx
                        )
                        val depositCharge = (50000..beforeReservation.firstCharge).random()

                        Service.getInstance()
                            .modifyReservationToIdx(
                                currentReservationIdx,
                                Reservation(
                                    userName = userName,
                                    roomNumber = roomNum,
                                    checkInDate = checkInDate,
                                    checkOutDate = checkOutDate,
                                    firstCharge = beforeReservation.firstCharge,
                                    depositCharge = depositCharge
                                )
                            )
                        val refundCash: Int =
                            (beforeReservation.depositCharge * calculateRefundRate(
                                beforeReservation.checkInDate
                            )).toInt()
                        Service.getInstance()
                            .addCharge(LogCharge(userName, Key.CHARGE_REFUND_MODIFY, refundCash))
                        Service.getInstance()
                            .addCharge(LogCharge(userName, Key.CHARGE_DEPOSIT, depositCharge))
                        println("수정이 완료 되었습니다.")
                    }

                    Key.RESERVATION_CANCEL -> {
                        Service.getInstance().removeReservationToIdx(currentReservationIdx)
                        val refundCash: Int =
                            (beforeReservation.depositCharge * calculateRefundRate(
                                beforeReservation.checkInDate
                            )).toInt()
                        Service.getInstance()
                            .addCharge(LogCharge(userName, Key.CHARGE_REFUND_CANCEL, refundCash))

                        println("[취소 유의사항]")
                        println("체크인 3일 이전 취소 예약금 환불 불가")
                        println("체크인 5일 이전 취소 예약금 30% 환불")
                        println("체크인 7일 이전 취소 예약금 50% 환불")
                        println("체크인 14일 이전 취소 예약금 80% 환불")
                        println("체크인 30일 이전 취소 예약금 100% 환불")
                        println("취소가 완료 되었습니다.")
                    }
                }
                return
            }
            println("범위에 없는 예약번호 입니다.")
        }
    }
}