package com.potatomeme.hotelreservationprogram.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object Utils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun isValidDate(input: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
            LocalDate.parse(input, formatter)
            true
        } catch (e: DateTimeParseException) {
            false
        }
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
}

