package com.ngr.aoc.y2023.day24

import com.ngr.aoc.Day
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import kotlin.math.roundToLong

class Day24 : Day<HailStone, Int, Long>() {

    private val mc = MathContext(20, RoundingMode.HALF_UP)

    override fun handleLine(lines: MutableList<HailStone>, line: String) {
        lines.add(HailStone.fromString(line))
    }

    override fun part1(lines: List<HailStone>): Int {
        val window = 200000000000000.0..400000000000000.0

        val allPairs = lines.flatMapIndexed { index, h ->
            lines.drop(index + 1).map { h to it }
        }

        return allPairs.count {
            it.first.intersection2DWith(it.second)
                ?.let { intersection ->
                    intersection.inWindow(xRange = window, yRange = window) &&
                            it.first.inFuture2D(intersection) &&
                            it.second.inFuture2D(intersection)
                } ?: false
        }
    }

    override fun part2(lines: List<HailStone>) =
        lines.sortedBy { it.v.x }
            .windowed(3)
            .first { it[0].v.x == it[1].v.x && it[0].v.x == it[2].v.x }
            .sortedBy { it.pos.x }
            .let { stones ->
                val s1 = stones[0]
                val s2 = stones[1]
                val s3 = stones[2]

                val x10 = BigDecimal.valueOf(s1.pos.x)
                val x20 = BigDecimal.valueOf(s2.pos.x)
                val x30 = BigDecimal.valueOf(s3.pos.x)

                val y10 = BigDecimal.valueOf(s1.pos.y)
                val y20 = BigDecimal.valueOf(s2.pos.y)
                val y30 = BigDecimal.valueOf(s3.pos.y)

                val z10 = BigDecimal.valueOf(s1.pos.z)
                val z20 = BigDecimal.valueOf(s2.pos.z)
                val z30 = BigDecimal.valueOf(s3.pos.z)

                val dx1 = BigDecimal.valueOf(s1.v.x).setScale(0)
                val dx2 = BigDecimal.valueOf(s2.v.x).setScale(0)
                val dx3 = BigDecimal.valueOf(s3.v.x).setScale(0)

                val dy1 = BigDecimal.valueOf(s1.v.y).setScale(0)
                val dy2 = BigDecimal.valueOf(s2.v.y).setScale(0)
                val dy3 = BigDecimal.valueOf(s3.v.y).setScale(0)

                val dz1 = BigDecimal.valueOf(s1.v.z).setScale(0)
                val dz2 = BigDecimal.valueOf(s2.v.z).setScale(0)
                val dz3 = BigDecimal.valueOf(s3.v.z).setScale(0)

                val factors = commonFactors(x20 - x10, x30 - x10)

                val rock = factors.flatMap { listOf(it, -it) }.sortedDescending().firstNotNullOf {

                    val dt12 = (x20 - x10).divide(it, mc)
                    val dt13 = (x30 - x10).divide(it, mc)

                    // dyr = ((y20 + dy2 * (t1 + dt12)) - (y10 + dy1 * t1)) / dt21
                    // dyr = ((y30 + dy3 * (t1 + dt13)) - (y10 + dy1 * t1)) / dt31
                    // => t1 = (dt12 / dt13 * (y30 + dy3 * dt31 - y10) - y20 - dy2 * dt21 + y10) / (dt21 / dt31 * (dy1 - dy3) + dy2 - dy1)

                    val t1_y =
                        (dt12.divide(dt13, mc) * (y30 + dy3 * dt13 - y10) - y20 - dy2 * dt12 + y10).divide(
                            (dt12.divide(dt13, mc) * (dy1 - dy3) + dy2 - dy1), mc
                        ).setScale(0, RoundingMode.HALF_UP)
                    val t1_z =
                        (dt12.divide(dt13, mc) * (z30 + dz3 * dt13 - z10) - z20 - dz2 * dt12 + z10).divide(
                            (dt12.divide(dt13, mc) * (dz1 - dz3) + dz2 - dz1), mc
                        ).setScale(0, RoundingMode.HALF_UP)

                    if (t1_y.compareTo(t1_z) != 0) {
                        return@firstNotNullOf null
                    }
                    val t1 = t1_y
                    val t2 = t1 + dt12
                    val t3 = t1 + dt13

                    val dxr = dx1 + it
                    val dyr = ((y20 + dy2 * t2) - (y10 + dy1 * t1)).divide(dt12)
                    val dzr = ((z20 + dz2 * t2) - (z10 + dz1 * t1)).divide(dt12)

                    val xr0 = x10 + dx1 * t1 - dxr * t1
                    val yr0 = y10 + dy1 * t1 - dyr * t1
                    val zr0 = z10 + dz1 * t1 - dzr * t1

                    HailStone(
                        pos = Point3D(xr0.toDouble(), yr0.toDouble(), zr0.toDouble()),
                        v = Point3D(dxr.toDouble(), dyr.toDouble(), dzr.toDouble())
                    )
                }
                (rock.pos.x + rock.pos.y + rock.pos.z).roundToLong()
            }
}