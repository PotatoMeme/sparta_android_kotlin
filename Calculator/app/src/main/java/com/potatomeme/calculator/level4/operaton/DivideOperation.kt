package com.potatomeme.calculator.level4.operaton

class DivideOperation : AbstractOperation() {
    override fun operate(a: Double, b: Double): Double {
        require(b != 0.0) { "b is zero. So it can't operate" }
        return a / b
    }
}