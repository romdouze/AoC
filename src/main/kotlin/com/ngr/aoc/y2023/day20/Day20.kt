package com.ngr.aoc.y2023.day20

import com.ngr.aoc.Day
import com.ngr.aoc.y2023.day20.Pulse.HIGH
import com.ngr.aoc.y2023.day20.Pulse.LOW

class Day20 : Day<Module, Long, Long>() {
    override fun handleLine(lines: MutableList<Module>, line: String) {
        lines.add(Module.fromString(line))
    }

    override fun part1(lines: List<Module>): Long {
        initMemories(lines)
        val modules = lines.associateBy { it.name }

        val signals = ArrayDeque<Signal>()
        val allSignals = signals.toMutableList()

        repeat(1000) {
            signals.add(Signal("button", "broadcaster", LOW))
            while (signals.isNotEmpty()) {
                val signal = signals.removeFirst()
                allSignals.add(signal)
                val outSignals = modules[signal.target]?.accept(signal) ?: emptyList()
                signals.addAll(outSignals)
            }
        }

        return allSignals.partition { it.pulse == LOW }
            .let {
                it.first.count().toLong() * it.second.count()
            }
    }

    override fun part2(lines: List<Module>): Long {
        initMemories(lines)
        val modules = lines.associateBy { it.name }

        val rxFeeders = modules.values.filter { it.destinations.contains("dg") }
            .map { it.name }.associateWith { 0L to 0L }
            .toMutableMap()
        val signals = ArrayDeque<Signal>()
        var count = 0L

        while (rxFeeders.values.any { it.first == 0L || it.second == 0L }) {
            signals.add(Signal("button", "broadcaster", LOW))
            val allSignals = mutableListOf<Signal>()
            while (signals.isNotEmpty()) {
                val signal = signals.removeFirst()
                allSignals.add(signal)
                val outSignals = modules[signal.target]?.accept(signal) ?: emptyList()
                signals.addAll(outSignals)
            }
            count++
            val highsToRx = allSignals.filter { rxFeeders.containsKey(it.source) && it.pulse == HIGH }
            if (highsToRx.isNotEmpty()) {
                highsToRx.filter { rxFeeders[it.source]!!.let { it.first == 0L || it.second == 0L } }
                    .forEach {
                        println("[$count] => $it")
                        if (rxFeeders[it.source]!!.first == 0L) {
                            rxFeeders[it.source] = count to 0L
                        } else {
                            rxFeeders[it.source] = rxFeeders[it.source]!!.first to count
                        }

                    }
            }
        }

        println(rxFeeders)

        return rxFeeders.values.fold(1) { acc, c -> acc * (c.second - c.first) }
    }

    private fun initMemories(modules: List<Module>) {
        modules.filterIsInstance<Conjunction>()
            .forEach { con ->
                con.initMemory(
                    modules.filter { it.destinations.contains(con.name) }.map { it.name }
                )
            }
    }
}