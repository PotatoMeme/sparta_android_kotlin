package com.team8.applemarket.util

import android.icu.text.DecimalFormat

object Util {
    fun Int.numFormatter(): String = DecimalFormat("#,###").format(this)
}