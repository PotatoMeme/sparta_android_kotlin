package com.potatomeme.calculator.level3.operaton

class DivideOperation {
    fun operate(a: Double, b: Double): Double {
        require(b != 0.0) { "b is zero. So it can't operate" }
        return a / b
    }
}