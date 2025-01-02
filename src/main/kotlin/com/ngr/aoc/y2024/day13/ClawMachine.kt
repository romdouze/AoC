package com.ngr.aoc.y2024.day13

import java.awt.Point
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class ClawMachine(
    buttonAStr: String,
    buttonBStr: String,
    prizeStr: String,
) {

    private companion object {
        private val BUTTON_PATTERN = "Button [AB]: X\\+(?<dx>\\d+), Y\\+(?<dy>\\d+)".toRegex()
        private val PRIZE_PATTERN = "Prize: X=(?<x>\\d+), Y=(?<y>\\d+)".toRegex()

        private const val EPSILON = 0.0001

        private const val PRIZE_OFFSET = 10000000000000
    }

    private val a = BUTTON_PATTERN.find(buttonAStr)!!
        .let { Point(it.groups["dx"]!!.value.toInt(), it.groups["dy"]!!.value.toInt()) }
    private val b = BUTTON_PATTERN.find(buttonBStr)!!
        .let { Point(it.groups["dx"]!!.value.toInt(), it.groups["dy"]!!.value.toInt()) }
    private val prize = PRIZE_PATTERN.find(prizeStr)!!
        .let { Point(it.groups["x"]!!.value.toInt(), it.groups["y"]!!.value.toInt()) }
    private val prizeOffset = PointL(prize.x + PRIZE_OFFSET, prize.y + PRIZE_OFFSET)

    private val winningSetupD = run {
        val m = ((a.y.toDouble() / a.x) * prize.x - prize.y) / (a.y.toDouble() * b.x / a.x - b.y)
        val n = (prize.x - m * b.x) / a.x

        Pair(n, m)
    }
    private val winningSetup = winningSetupD.first.roundToInt() to winningSetupD.second.roundToInt()
    val winCost = winningSetup.first * 3 + winningSetup.second
    val isWinnable = winningSetupD.first.closeTo(winningSetup.first) &&
            winningSetupD.second.closeTo(winningSetup.second)

    private val winningSetupDOffset = run {
        val m = ((a.y.toDouble() / a.x) * prizeOffset.x - prizeOffset.y) / (a.y.toDouble() * b.x / a.x - b.y)
        val n = (prizeOffset.x - m * b.x) / a.x

        Pair(n, m)
    }
    private val winningSetupOffset = winningSetupDOffset.first.roundToLong() to winningSetupDOffset.second.roundToLong()
    val winCostOffset = winningSetupOffset.first * 3 + winningSetupOffset.second
    val isWinnableOffset = winningSetupDOffset.first.closeTo(winningSetupOffset.first) &&
            winningSetupDOffset.second.closeTo(winningSetupOffset.second)

    private fun Double.closeTo(other: Int) =
        abs(this - other) < EPSILON

    private fun Double.closeTo(other: Long) =
        abs(this - other) < EPSILON
}

data class PointL(val x: Long, val y: Long)

//n*ax + m * bx = X
//n*ay + m * by = Y
//
//n = (X - m * bx) / ax
//
//(ay * (X - m * bx) / ax) + m * by = Y
//(ay / ax) * (X - m * bx) = Y - m * by
//(ay / ax) * X - (ay / ax) * m * bx + m * by = Y
//(ay / ax) * X - Y = (ay / ax) * m * bx - m * by
//(ay / ax) * X - Y = m * (ay * bx / ax - by)
//
//m = ((ay / ax) * X - Y) / (ay * bx / ax - by)