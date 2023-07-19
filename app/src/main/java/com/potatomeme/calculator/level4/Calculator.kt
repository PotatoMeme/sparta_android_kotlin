package com.potatomeme.calculator.level4

//책임 : 전체적인 동작을 관리
class Calculator {
    fun calculate(key: Int, numA: Double, numB: Double): String {
        val operator: AbstractOperation = when (key) {
            Key.CALCULATE_ADD -> AddOperation()
            Key.CALCULATE_SUBTRACT -> SubtractOperation()
            Key.CALCULATE_MULTIPLY -> MultiplyOperation()
            Key.CALCULATE_DIVIDE -> DivideOperation()
            else -> throw IllegalArgumentException("key($key) is over range")
        }
        return "계산결과 : ${operator.operate(numA, numB)}"
    }
}