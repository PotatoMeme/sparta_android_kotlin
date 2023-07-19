package com.potatomeme.calculator.level1

import java.lang.IllegalArgumentException

class Calculator {

    private val keys = setOf(
        Key.CALCULATE_STOP,
        Key.CALCULATE_ADD,
        Key.CALCULATE_MINUS,
        Key.CALCULATE_MULTIPLY,
        Key.CALCULATE_DEVIDE,
    )

    fun calculate() {
        println("계산을 진행하겠습니다.")
        println(
            "계산의 종류\n" +
                    "-----------------------------------------------------------\n" +
                    "0 : 종료 | 1 : 더하기 | 2 : 빼기 | 3 : 곱하기 | 4 : 나누기\n" +
                    "-----------------------------------------------------------"
        )

        // 계산 종류 확인용
        val key: Int
        println("[계산의 종류]를 입력해주세요")
        while (true) {
            val line = readLine()!!
            if (line.toIntOrNull() != null && line.toInt() in keys) {
                key = line.toInt()
                break
            } else {
                println("값이 맞지 않습니다! 값을 확인해주세요!")
            }

        }

        if (key == Key.CALCULATE_STOP) {
            println("계산을 종료하겠습니다.")
            return
        }

        val numA: Double
        val numB: Double

        // 계산 인자 확인용
        println("[인자A] [인자B] 위 양식대로 값을 입력해주세요")
        while (true) {
            val readList = readLine()!!.split(" ")
            if (readList.size != 2) {
                println("양식대로 값을 넣어주세요")
                continue
            }

            val numAIsCurrect = readList[0].toDoubleOrNull() != null
            val numBIsCurrect =
                readList[1].toDoubleOrNull() != null && (key != Key.CALCULATE_DEVIDE || readList[1].toDouble() != 0.0)

            if (numAIsCurrect && numBIsCurrect) {
                numA = readList[0].toDouble()
                numB = readList[1].toDouble()
                break
            }

            if (!numAIsCurrect) println("인자A값을 확인해주세요")
            if (!numBIsCurrect) println("인자B값을 확인해주세요, 나누기의 경우 인자B의경우 0을 넣으실수 없습니다")
        }

        when (key) {
            Key.CALCULATE_ADD -> println("계산결과 : ${add(numA, numB)}")
            Key.CALCULATE_MINUS -> println("계산결과 : ${minus(numA, numB)}")
            Key.CALCULATE_MULTIPLY -> println("계산결과 : ${multiply(numA, numB)}")
            Key.CALCULATE_DEVIDE -> println("계산결과 : ${devide(numA, numB)}")
        }

        println("계산을 계속 하시겠습니까?[y,n]")
        if (readLine() == "y") calculate() else {
            println("계산을 종료하겠습니다.")
            return
        }
    }

    private fun add(a: Double, b: Double) = a + b
    private fun minus(a: Double, b: Double) = a - b
    private fun multiply(a: Double, b: Double) = a * b
    private fun devide(a: Double, b: Double): Double = a / b
}