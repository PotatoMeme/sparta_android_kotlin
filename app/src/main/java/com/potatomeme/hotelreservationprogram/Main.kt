package com.potatomeme.hotelreservationprogram

import android.os.Build
import androidx.annotation.RequiresApi
import com.potatomeme.hotelreservationprogram.model.LogCharge
import com.potatomeme.hotelreservationprogram.model.Reservation
import com.potatomeme.hotelreservationprogram.service.Service
import com.potatomeme.hotelreservationprogram.util.ShowRule
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
                while (true) {
                    val currentCheckInDate = rule.showSelectCheckInDate()
                    if (service.checkCheckInDate(roomNum, currentCheckInDate)) {
                        checkInDate = currentCheckInDate
                        break
                    }
                    System.err.println("해당 날짜에 이미 방을 사용중입니다. 다른 날짜를 입력해주세요")
                }
                val checkOutDate: String
                while (true) {
                    val currentCheckOutDate = rule.showSelectCheckOutDate(checkInDate)
                    if (service.checkCheckOutDate(roomNum, checkInDate, currentCheckOutDate)) {
                        checkOutDate = currentCheckOutDate
                        break
                    }
                    System.err.println("해당 날짜에 이미 방을 사용중입니다. 다른 날짜를 입력해주세요")
                }
                val firstCharge = (100000..200000).random()
                val depositCharge = (50000..firstCharge).random()

                service.addReservation(
                    Reservation(
                        userName,
                        roomNum,
                        checkInDate,
                        checkOutDate,
                        firstCharge,
                        depositCharge
                    )
                )
                service.addCharge(LogCharge(userName, Key.CHARGE_FIRST, firstCharge))
                service.addCharge(LogCharge(userName, Key.CHARGE_DEPOSIT, firstCharge))
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
                val userName: String = rule.showSelectName(Key.MENU_PRINT_CHARGE_HISTORY)
                print(service.getUserChargeLog(userName))
            }

            Key.MENU_RESERVATION_MODIFY -> {
                val userName: String = rule.showSelectName(Key.MENU_PRINT_CHARGE_HISTORY)
                val reservationList: List<Pair<Int, String>> =
                    service.getUserReservationListWithIndex(userName)
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
                        if (line == "exit") {
                            break
                        }
                        if (line.toIntOrNull() != null && line.toInt() in 1..reservationList.size) {
                            selectedNum = line.toInt() - 1
                            val currentReservationIdx: Int = reservationList[selectedNum].first
                            val beforeReservation: Reservation =
                                service.reservationList[currentReservationIdx]
                            println("해당 예약을 어떻게 하시겠습니까? 1. 변경 2. 취소 / 이외번호. 메뉴로 돌아가기")
                            when (readLine()!!.toIntOrNull()) {
                                Key.RESERVATION_MODIFY -> {
                                    val roomNum: Int = rule.showSelectRoomName()
                                    val checkInDate: String
                                    while (true) {
                                        val currentCheckInDate = rule.showSelectCheckInDate()
                                        if (service.checkCheckInDate(
                                                roomNum,
                                                currentCheckInDate,
                                                currentReservationIdx
                                            )
                                        ) {
                                            checkInDate = currentCheckInDate
                                            break
                                        }
                                        System.err.println("해당 날짜에 이미 방을 사용중입니다. 다른 날짜를 입력해주세요")
                                    }
                                    val checkOutDate: String
                                    while (true) {
                                        val currentCheckOutDate =
                                            rule.showSelectCheckOutDate(checkInDate)
                                        if (service.checkCheckOutDate(
                                                roomNum,
                                                checkInDate,
                                                currentCheckOutDate,
                                                currentReservationIdx
                                            )
                                        ) {
                                            checkOutDate = currentCheckOutDate
                                            break
                                        }
                                        System.err.println("해당 날짜에 이미 방을 사용중입니다. 다른 날짜를 입력해주세요")
                                    }
                                    val depositCharge = (50000..beforeReservation.firstCharge).random()
                                    service.modifyReservationToIdx(
                                        currentReservationIdx,
                                        Reservation(
                                            userName,
                                            roomNum,
                                            checkInDate,
                                            checkOutDate,
                                            beforeReservation.firstCharge,
                                            depositCharge
                                        )
                                    )
                                    val refundCash: Int =
                                        (beforeReservation.depositCharge * calculateRefundRate(
                                            beforeReservation.checkInDate
                                        )).toInt()
                                    service.addCharge(LogCharge(userName,Key.CHARGE_REFUND_MODIFY,refundCash))
                                    service.addCharge(LogCharge(userName,Key.CHARGE_DEPOSIT,depositCharge))
                                    println("수정이 완료 되었습니다.")
                                }

                                Key.RESERVATION_CANCEL -> {
                                    service.removeReservationToIdx(currentReservationIdx)
                                    val refundCash: Int =
                                        (beforeReservation.depositCharge * calculateRefundRate(
                                            beforeReservation.checkInDate
                                        )).toInt()
                                    service.addCharge(
                                        LogCharge(
                                            userName,
                                            Key.CHARGE_REFUND_CANCEL,
                                            refundCash
                                        )
                                    )
                                    println("[취소 유의사항]")
                                    println("체크인 3일 이전 취소 예약금 환불 불가")
                                    println("체크인 5일 이전 취소 예약금 30% 환불")
                                    println("체크인 7일 이전 취소 예약금 50% 환불")
                                    println("체크인 14일 이전 취소 예약금 80% 환불")
                                    println("체크인 30일 이전 취소 예약금 100% 환불")
                                    println("취소가 완료 되었습니다.")
                                }
                            }
                            break
                        }
                        println("범위에 없는 예약번호 입니다.")
                    }

                }
            }
        }
    }
    println("호텔 예약 프로그램을 종료 합니다.")
}

fun dateDifferenceInDays(date1: String, date2: String): Long {
    val dateFormat = SimpleDateFormat("yyyyMMdd")
    val parsedDate1 = dateFormat.parse(date1)
    val parsedDate2 = dateFormat.parse(date2)

    val differenceInMillis = parsedDate2.time - parsedDate1.time
    return differenceInMillis / (24 * 60 * 60 * 1000)
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateRefundRate(checkInDate: String): Double {
    val now: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    val differenceInDays: Long = dateDifferenceInDays(now, checkInDate)

    return when {
        differenceInDays >= 30 -> 1.0 // 30일 이전이면 100% 환불 (1.0 = 100%)
        differenceInDays >= 14 -> 0.8 // 14일 이전이면 80% 환불 (0.8 = 80%)
        differenceInDays >= 7 -> 0.5  // 7일 이전이면 50% 환불 (0.5 = 50%)
        differenceInDays >= 5 -> 0.3  // 5일 이전이면 30% 환불 (0.3 = 30%)
        else -> 0.0                  // 그 외의 경우 0% 환불 (0.0 = 0%)
    }
}