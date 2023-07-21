package com.potatomeme.calculator.level3


//유효성 검사에대한 책임
class ShowRule {
    fun getArguments(): Triple<Int, Double, Double> {
        println("계산을 진행하겠습니다.")
        println(
            "계산의 종류\n" +
                    "------------------------------------------------------------------------\n" +
                    "0 : 종료 | 1 : 더하기 | 2 : 빼기 | 3 : 곱하기 | 4 : 나누기\n" +
                    "------------------------------------------------------------------------"
        )

        // 계산 종류 확인용
        val key: Int
        println("[계산의 종류]를 입력해주세요")
        while (true) {
            val line = readLine()!!
            if (line.toIntOrNull() != null && line.toInt() in Key.keys) {
                key = line.toInt()
                break
            } else {
                println("값이 맞지 않습니다! 값을 확인해주세요!")
            }
        }

        if (key == Key.CALCULATE_STOP) {
            return Triple(Key.CALCULATE_STOP, 0.0, 0.0)
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
                readList[1].toDoubleOrNull() != null && ((key != Key.CALCULATE_DIVIDE) || readList[1].toDouble() != 0.0)

            if (numAIsCurrect && numBIsCurrect) {
                numA = readList[0].toDouble()
                numB = readList[1].toDouble()
                break
            }

            if (!numAIsCurrect) println("인자A값을 확인해주세요")
            if (!numBIsCurrect) println("인자B값을 확인해주세요")
        }
        return Triple(key, numA, numB)
    }
}