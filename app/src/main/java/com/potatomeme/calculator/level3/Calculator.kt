package com.potatomeme.calculator.level3

//책임 : 전체적인 동작을 관리
class Calculator {
    fun calculate(key: Int, numA: Double, numB: Double): String {
        return "계산결과 : ${
            when (key) {
                Key.CALCULATE_ADD -> add(numA, numB)
                Key.CALCULATE_SUBTRACT -> subtract(numA, numB)
                Key.CALCULATE_MULTIPLY -> multiply(numA, numB)
                Key.CALCULATE_DIVIDE -> divide(numA, numB)
                else -> throw IllegalArgumentException("key($key) is over range")
            }
        }"
    }

    private fun add(a: Double, b: Double) = AddOperation().operate(a, b)
    private fun subtract(a: Double, b: Double) = SubtractOperation().operate(a, b)
    private fun multiply(a: Double, b: Double) = MultiplyOperation().operate(a, b)
    private fun divide(a: Double, b: Double) = DivideOperation().operate(a, b)
}