package com.ngr.aoc.y2022.day21

import com.ngr.aoc.Day

class Day21 : Day<Monkey, Long, Long>() {

    private val monkeys = mutableMapOf<String, Monkey>()

    override fun handleLine(lines: MutableList<Monkey>, line: String) {
        Monkey.fromString(line)
            .also { monkeys[it.id] = it }
    }

    override fun part1(lines: List<Monkey>) =
        monkeys["root"]!!.performJob(monkeys)

    override fun part2(lines: List<Monkey>) =
        (monkeys["root"]!!.job as Operation).let { rootJob ->
            if (monkeys[rootJob.left]!!.isAdjustable(monkeys)) {
                monkeys[rootJob.left]!!.resolveForTarget(monkeys[rootJob.right]!!.yelled!!, monkeys)
            } else {
                monkeys[rootJob.right]!!.resolveForTarget(monkeys[rootJob.left]!!.yelled!!, monkeys)
            }
        }
}