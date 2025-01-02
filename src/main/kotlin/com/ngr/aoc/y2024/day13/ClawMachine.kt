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

        private const val EPSILON = 0.0000001

        private const val PRIZE_OFFSET = 10000000000000
    }

    private val a = BUTTON_PATTERN.find(buttonAStr)!!
        .let { Point(it.groups["dx"]!!.value.toInt(), it.groups["dy"]!!.value.toInt()) }
    private val b = BUTTON_PATTERN.find(buttonBStr)!!
        .let { Point(it.groups["dx"]!!.value.toInt(), it.groups["dy"]!!.value.toInt()) }
    private val prize = PRIZE_PATTERN.find(prizeStr)!!
        .let { Point(it.groups["x"]!!.value.toInt(), it.groups["y"]!!.value.toInt()) }

    val winningSetupD = run {
        val m = ((a.y.toDouble() / a.x) * prize.x - prize.y) / (a.y.toDouble() * b.x / a.x - b.y)
        val n = (prize.x - m * b.x) / a.x

        Pair(n, m)
    }
    val winningSetup = winningSetupD.first.roundToInt() to winningSetupD.second.roundToInt()
    val winCost = winningSetup.first * 3 + winningSetup.second
    val isWinnable = winningSetupD.first.closeTo(winningSetup.first) &&
            winningSetupD.second.closeTo(winningSetup.second)

    val winningSetupDOffset = run {
//m = (Y - (ay / ax) * X) / (- (ay / ax) * bx + by) + (1 - ay / ax) / (- (ay / ax) * bx + by) * 10t
        val m =
            (prize.y - (a.y.toDouble() / a.x) * prize.x) / (-(a.y.toDouble() / a.x) * b.x + b.y) +
                    (1 - a.y.toDouble() / a.x) / (-(a.y.toDouble() / a.x) * b.x + b.y) * PRIZE_OFFSET
//n = (X - m * bx) / ax + 10t / ax
        val n = (prize.x - m * b.x) / a.x + PRIZE_OFFSET.toDouble() / a.x

        Pair(n, m)
    }
    val winningSetupOffset = winningSetupDOffset.first.roundToLong() to winningSetupDOffset.second.roundToLong()
    val winCostOffset = winningSetupOffset.first * 3 + winningSetupOffset.second
    val isWinnableOffset = winningSetupDOffset.first.closeTo(winningSetupOffset.first) &&
            winningSetupDOffset.second.closeTo(winningSetupOffset.second)

    private fun Double.closeTo(other: Int) =
        abs(this - other) < EPSILON

    private fun Double.closeTo(other: Long) =
        abs(this - other) < EPSILON
}

data class PointL(val x: Long, val y: Long)

//n <= 100
//m <= 100
//
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


//n*ax + m * bx = X + 10t
//n*ay + m * by = Y + 10t
//
//n = (X - m * bx) / ax + 10t / ax
//
//ay * (X - m * bx) / ax + m * by = Y + 10t - ay / ax * 10t
//ay * (X - m * bx) / ax + m * by = Y + 10t - ay / ax * 10t
//(ay / ax) * X - (ay / ax) * m * bx + m * by = Y + (1 - ay / ax) * 10t
//- (ay / ax) * m * bx + m * by = Y - (ay / ax) * X + (1 - ay / ax) * 10t
//m * (- (ay / ax) * bx + by) = Y - (ay / ax) * X + (1 - ay / ax) * 10t
//m = (Y - (ay / ax) * X) / (- (ay / ax) * bx + by) + (1 - ay / ax) / (- (ay / ax) * bx + by) * 10t


//(ay / ax) * (X + 10t) - (ay / ax) * m * bx + m * by = Y + 10t
//(ay / ax) * (X + 10t) - Y - 10t = (ay / ax) * m * bx - m * by
//(ay / ax) * (X + 10t) - Y - 10t = m * (ay * bx / ax - by)
//(ay / ax) * X - Y + (ay / ax - 1) * 10t = m * (ay * bx / ax - by)
//
//m = ((ay / ax) * X - Y + (ay / ax - 1) * 10t) / (ay * bx / ax - by)