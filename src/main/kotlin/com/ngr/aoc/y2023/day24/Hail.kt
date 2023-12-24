package com.ngr.aoc.y2023.day24

import kotlin.math.floor
import kotlin.math.sign
import kotlin.math.sqrt

data class HailStone(
    val pos: Point3D,
    val v: Point3D,
) {
    private val m2D = v.y / v.x

    // m(x - x1) = (y - y1)
    // mx -y + y1 - mx1 = 0
    // ax + by + c = 0
    private val a = m2D
    private val b = -1.0
    private val c = -m2D * pos.x + pos.y

    companion object {
        fun fromString(string: String) =
            string.split("@")
                .map { it.trim() }
                .let {
                    HailStone(
                        Point3D.fromString(it[0]),
                        Point3D.fromString(it[1]),
                    )
                }
    }

    // (
    //   (b1c2 - b2c1) / (a1b2 - a2b1),
    //   (a2c1 - a1c2) / (a1b2 - a2b1)
    // )
    fun intersection2DWith(other: HailStone) =
        (a * other.b - other.a * b)
            .takeIf { it.sign != 0.0 }
            ?.let {
                Point3D(
                    (b * other.c - other.b * c) / it,
                    (other.a * c - a * other.c) / it,
                    0.0
                )
            }

    fun inFuture2D(point: Point3D) =
        v.x.sign == (point.x - pos.x).sign &&
                v.y.sign == (point.y - pos.y).sign

    fun posAt(t: Long) =
        Point3D(
            pos.x * t,
            pos.y * t,
            pos.z * t,
        )

}

data class Point3D(
    val x: Double,
    val y: Double,
    val z: Double,
) {
    companion object {
        fun fromString(string: String) =
            string.split(", ")
                .map { it.trim().toDouble() }
                .let { Point3D(it[0], it[1], it[2]) }
    }

    fun inWindow(
        xRange: ClosedFloatingPointRange<Double>? = null,
        yRange: ClosedFloatingPointRange<Double>? = null,
        zRange: ClosedFloatingPointRange<Double>? = null
    ) =
        (xRange?.contains(x) ?: true) &&
                (yRange?.contains(y) ?: true) &&
                (zRange?.contains(z) ?: true)

    operator fun plus(other: Point3D) =
        Point3D(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Point3D) =
        Point3D(x - other.x, y - other.y, z - other.z)

    operator fun times(t: Double) =
        Point3D(x * t, y * t, z * t)
}

class Factors(val a: Double) {

    private var v = a
    private val factors = mutableListOf(1.0)

    fun factors(): List<Double> {
        addFactorPows(2.0)
        var s = floor(sqrt(v))

        IntProgression.fromClosedRange(3, s.toInt(), 2)
            .forEach {
                if (addFactorPows(it.toDouble())) {
                    s = floor(sqrt(v))
                }
            }

        if (v != 1.0) {
            addFactorPows(v)
        }

        return factors.sorted()
    }

    private fun addFactors(fs: List<Double>) {
        factors.addAll(fs.flatMap { f -> factors.map { f * it } })
    }

    private fun addFactorPows(f: Double): Boolean {
        var p = 1.0
        val pows = mutableListOf<Double>()
        while (v % f == 0.0) {
            v /= f
            p *= f
            pows.add(p)
        }
        addFactors(pows)
        return pows.isNotEmpty()
    }
}

fun commonFactors(a: Double, b: Double) =
    Factors(gcd(a, b)).factors()

private fun gcd(a: Double, b: Double): Double =
    if (a > 0.0) gcd(b % a, a) else b