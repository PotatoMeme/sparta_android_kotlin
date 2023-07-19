package com.potatomeme.calculator.level4

//책임 : 전체적인 동작을 관리
class Calculator {

    fun calculate() {
        while (true) {
            //값에대한 유효성 검사
            val (key, numA, numB) = ShowRule().getArguments()
            if (key == Key.CALCULATE_STOP) break

            val operator: AbstractOperation = when (key) {
                Key.CALCULATE_ADD -> AddOperation()
                Key.CALCULATE_SUBTRACT -> SubtractOperation()
                Key.CALCULATE_MULTIPLY -> MultiplyOperation()
                Key.CALCULATE_DIVIDE -> DivideOperation()
                else -> return
            }
            println("계산결과 : ${operator.operate(numA, numB)}")

            println("계산을 계속 하시겠습니까?[y]")
            if (readLine() != "y") break
        }
        println("계산을 종료하겠습니다.")
    }
}