package com.ngr.aoc.y2023.day7

import com.ngr.aoc.y2023.day7.Type.FIVE
import com.ngr.aoc.y2023.day7.Type.FOUR
import com.ngr.aoc.y2023.day7.Type.HIGH
import com.ngr.aoc.y2023.day7.Type.PAIR
import com.ngr.aoc.y2023.day7.Type.THREE
import com.ngr.aoc.y2023.day7.Type.TWO_PAIRS

class Hand(
    private val handStr: String,
    val bid: Long
) : Comparable<Hand> {

    companion object {
        val comparatorWithJoker = Comparator<Hand> { h1, h2 ->
            if (h1.handTypeWithJokers == h2.handTypeWithJokers) {
                h1.handSortableWithJoker.compareTo(h2.handSortableWithJoker)
            } else {
                h1.handTypeWithJokers.compareTo(h2.handTypeWithJokers)
            }
        }
    }

    private val handType = handStr.getHandType()
    private val handTypeWithJokers =
        if (!handStr.contains('J')) handType
        else {
            val cards = handStr.filter { it != 'J' }
                .toSet()
            if (cards.isEmpty()) FIVE
            else {
                cards.maxOf { replacement ->
                    handStr.replace('J', replacement)
                        .getHandType()
                }
            }
        }

    private val handSortable = handStr.map {
        when (it) {
            '2' -> 'A'
            '3' -> 'B'
            '4' -> 'C'
            '5' -> 'D'
            '6' -> 'E'
            '7' -> 'F'
            '8' -> 'G'
            '9' -> 'H'
            'T' -> 'I'
            'J' -> 'J'
            'Q' -> 'K'
            'K' -> 'L'
            'A' -> 'M'
            else -> throw IllegalArgumentException("Invalid hand [$handStr]")
        }
    }.joinToString("")

    private val handSortableWithJoker = handSortable
        .replace('J', ' ')

    override fun compareTo(other: Hand) =
        if (handType == other.handType) {
            handSortable.compareTo(other.handSortable)
        } else {
            handType.compareTo(other.handType)
        }
}

fun String.getHandType() =
    groupBy { it }
        .map { it.value.size }
        .let {
            val sorted = it.sortedDescending()
            when (sorted.first()) {
                5 -> FIVE
                4 -> FOUR
                3 -> if (sorted.last() == 2) Type.FULL
                else THREE

                2 -> if (sorted[1] == 2) TWO_PAIRS
                else PAIR

                else -> HIGH
            }
        }

enum class Type {
    HIGH, PAIR, TWO_PAIRS, THREE, FULL, FOUR, FIVE,
}