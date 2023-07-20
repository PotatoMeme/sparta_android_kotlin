package com.potatomeme.hotelreservationprogram

object Key {
    const val MENU_RESERVATION = 1
    const val MENU_PRINT_RESERVATION_LIST = 2
    const val MENU_PRINT_RESERVATION_SORTED_LIST = 3
    const val MENU_EXIT = 4
    const val MENU_PRINT_CHARGE_HISTORY = 5
    const val MENU_RESERVATION_MODIFY = 6
    val MENU_KEYS = setOf(
        MENU_RESERVATION,
        MENU_PRINT_RESERVATION_LIST,
        MENU_PRINT_RESERVATION_SORTED_LIST,
        MENU_EXIT,
        MENU_PRINT_CHARGE_HISTORY,
        MENU_RESERVATION_MODIFY,
    )
}