package com.ngr.aoc.y2022.day11

import java.math.BigInteger

data class WorryLevel(
    val remaindersBy: Map<Int, Int>,
) {

    constructor(value: Int) :
            this(divisibleBy.associateWith { value % it })


    private companion object {
        val divisibleBy = listOf(2, 3, 5, 7, 11, 13, 17, 19, 23)
    }

    operator fun div(other: BigInteger) =
        WorryLevel(newRemaindersDiv(other))

    operator fun plus(other: BigInteger) =
        WorryLevel(newRemaindersPlus(other))

    operator fun times(other: BigInteger) =
        WorryLevel(newRemaindersTimes(other))

    fun square() =
        WorryLevel(newRemaindersSquare())

    // (a / b) % n = ((a % n)((b^-1) % n)) % n
    // We only ever divide by 3 or 1.
    private fun newRemaindersDiv(other: BigInteger) =
        if (other == BigInteger.ONE) {
            remaindersBy.toMap()
        } else {
            remaindersBy.entries
                .associate {
                    // /!\ Is not defined when other and it.key are not coprimes
                    it.key to ((remaindersBy[it.key]!! * other.modInverse(it.key.toBigInteger()).toInt()) % it.key)
                }
        }

    // (a + b) % n = ((a % n) + (b % n)) % n
    private fun newRemaindersPlus(other: BigInteger) =
        remaindersBy.entries
            .associate {
                it.key to (remaindersBy[it.key]!! + other.toInt() % it.key) % it.key
            }

    // ab % n = ((a % n)(b % n) % n
    private fun newRemaindersTimes(other: BigInteger) =
        remaindersBy.entries
            .associate {
                it.key to (remaindersBy[it.key]!! * other.toInt() % it.key) % it.key
            }

    // a² % n = (a % n)² % n
    private fun newRemaindersSquare() =
        remaindersBy.entries
            .associate {
                it.key to (remaindersBy[it.key]!! * remaindersBy[it.key]!!) % it.key
            }
}