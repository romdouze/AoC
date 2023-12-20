package com.ngr.aoc.y2023.day20

import com.ngr.aoc.Day
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

        val signals = ArrayDeque<Signal>()
        var lowToRx = false
        var count = 0L

        while (!lowToRx) {
            signals.add(Signal("button", "broadcaster", LOW))
            while (signals.isNotEmpty()) {
                val signal = signals.removeFirst()
                val outSignals = modules[signal.target]?.accept(signal) ?: emptyList()
                signals.addAll(outSignals)
                outSignals.filter { it.target == "rx" }.forEach {
                    if (count % 1000000L == 0L) println("$count -> $it")
                    lowToRx = lowToRx || it.pulse == LOW
                }
            }
            count++
        }

        return count
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