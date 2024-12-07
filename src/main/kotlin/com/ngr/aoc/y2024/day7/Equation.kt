package com.ngr.aoc.y2024.day7

import kotlin.math.pow

class Equation(
    val testValue: Long,
    val numbers: List<Long>,
) {
    fun canBeTrue(operators: List<Operator>) =
        (0 until operators.size.toDouble().pow(numbers.lastIndex).toInt())
            .map { it.toString(radix = operators.size).padStart(numbers.lastIndex, '0') }
            .any { combination ->
                numbers.drop(1)
                    .zip(combination.map { it.digitToInt() })
                    .fold(numbers[0]) { acc, n ->
                        operators[n.second].calc(acc, n.first)
                    } == testValue
            }


}

enum class Operator(val calc: (Long, Long) -> Long) {
    ADD({ a, b -> a + b }),
    MULTIPLY({ a, b -> a * b }),
    CONCATENATE({ a, b -> "$a$b".toLong() }),
}