package com.potatomeme.calculator.level4

object Key {
    const val CALCULATE_STOP = 0
    const val CALCULATE_ADD = 1
    const val CALCULATE_SUBTRACT = 2
    const val CALCULATE_MULTIPLY = 3
    const val CALCULATE_DIVIDE = 4

    val keys = setOf(
        CALCULATE_STOP,
        CALCULATE_ADD,
        CALCULATE_SUBTRACT,
        CALCULATE_MULTIPLY,
        CALCULATE_DIVIDE,
    )
}