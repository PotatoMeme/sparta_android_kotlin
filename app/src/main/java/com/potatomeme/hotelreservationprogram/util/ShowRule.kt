package com.potatomeme.hotelreservationprogram.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.potatomeme.hotelreservationprogram.Key
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

//입력에 대한 유효성검사
class ShowRule {
    fun showMenuSelect(): Int {
        while (true) {
            println("[메뉴]")
            println("1. 방예약, 2. 예약목록 출력, 3. 예약목록 (정렬) 출력, 4. 시스템 종료, 5. 금액 입금-출금 내역 목록 출력, 6. 예약 변경/취소")
            val num = readLine()!!.toIntOrNull()
            if (num != null && num in Key.MENU_KEYS) return num
            System.err.println("범위안의 값을 입력해주세요")
        }
    }

    fun showSelectName(type:Int): String {
        val questionStr = when(type){
            Key.MENU_RESERVATION ->"예약자분의 성함을 입력해주세요"
            Key.MENU_PRINT_CHARGE_HISTORY -> "조회하실 사용자의 이름을 입력해주세요"
            Key.MENU_RESERVATION_MODIFY->"예약을 변경할 사용자 이름을 입력하세요"
            else -> throw IllegalArgumentException("unknown type($type)")
        }
        while (true) {
            println(questionStr)
            val name = readLine()
            if (!name.isNullOrBlank()) return name
            System.err.println("공백입력은 되지않습니다. 이름을 입력해주세요")
        }
    }

    fun showSelectRoomName(): Int {
        while (true) {
            println("예약할 방번호를 입력해주세요")
            val num = readLine()!!.toIntOrNull()
            if (num != null && num in 100..999) return num
            System.err.println("올바르지 않은 방번호 입니다. 방번호는  100 ~ 999 영역 이내입니다")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showSelectCheckInDate(): String {
        val now: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        while (true) {
            println("체크인 날짜를 입력해주세요. 20230631")
            val date = readLine()!!
            if (isValidDate(date)){
                if (now > date) System.err.println("채크인은 지난날을 선택할수 없습니다") else return date
            }else{
                System.err.println("해당값은 양식에 맞지않습니다. 값을 확인해주세요")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showSelectCheckOutDate(beforeDate: String) : String {
        require(isValidDate(beforeDate)){"beforeData($beforeDate) is not yyyyMMdd. check again"}
        while (true) {
            println("체크아웃 날짜를 입력해주세요. 20230631")
            val date = readLine()!!
            if (isValidDate(date)){
                if (beforeDate >= date) System.err.println("체크인 날짜보다 이전이거나 같을수 없습니다") else return date
            }else{
                System.err.println("해당값은 양식에 맞지않습니다. 값을 확인해주세요")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isValidDate(input: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
            LocalDate.parse(input, formatter)
            true
        } catch (e: DateTimeParseException) {
            false
        }
    }
}