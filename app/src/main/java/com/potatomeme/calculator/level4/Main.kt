package com.potatomeme.calculator.level4

fun main() {
    val calculator: Calculator = Calculator()
    val rule: ShowRule = ShowRule()
    while (true) {
        //값에대한 유효성 검사
        val (key, numA, numB) = rule.getArguments()
        if (key == Key.CALCULATE_STOP) break
        try {
            val result = calculator.calculate(key, numA, numB)
            println(result)
        } catch (illegalArgumentException: IllegalArgumentException) {
            println(illegalArgumentException)
            continue
        }
        println("계산을 계속 하시겠습니까?[y]")
        if (readLine() != "y") break
    }
    println("계산을 종료하겠습니다.")
}