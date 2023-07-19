package com.potatomeme.calculator.level3

//책임 : 전체적인 동작을 관리
class Calculator {

    fun calculate() {
        //값에대한 유효성 검사
        val (key, numA, numB) = ShowRule().getArguments()

        if (key == Key.CALCULATE_STOP) {
            println("계산을 종료하겠습니다.")
            return
        }


        when (key) {
            Key.CALCULATE_ADD -> println("계산결과 : ${add(numA, numB)}")
            Key.CALCULATE_SUBTRACT -> println("계산결과 : ${subtract(numA, numB)}")
            Key.CALCULATE_MULTIPLY -> println("계산결과 : ${multiply(numA, numB)}")
            Key.CALCULATE_DIVIDE -> println("계산결과 : ${divide(numA, numB)}")
        }

        println("계산을 계속 하시겠습니까?[y]")
        if (readLine() == "y") calculate() else {
            println("계산을 종료하겠습니다.")
            return
        }
    }

    private fun add(a: Double, b: Double) = AddOperation().operate(a, b)
    private fun subtract(a: Double, b: Double) = SubtractOperation().operate(a, b)
    private fun multiply(a: Double, b: Double) = MultiplyOperation().operate(a, b)
    private fun divide(a: Double, b: Double) = DivideOperation().operate(a, b)
}