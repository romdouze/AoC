package com.ngr.aoc.y2023.day20

import com.ngr.aoc.y2023.day20.Pulse.HIGH
import com.ngr.aoc.y2023.day20.Pulse.LOW


abstract class Module(
    val name: String,
    val destinations: List<String>,
) {
    companion object {
        private val MODULE_PATTERN = "(?<prefix>[%&]?)(?<name>\\w+) -> (?<destinations>.+)".toRegex()

        fun fromString(string: String) =
            MODULE_PATTERN.find(string)!!.groups.let {
                val prefix = it["prefix"]!!.value
                val name = it["name"]!!.value
                val destinations = it["destinations"]!!.value.split(", ")

                when (prefix) {
                    "%" -> FlipFlop(name, destinations)
                    "&" -> Conjunction(name, destinations)
                    "" -> Broadcast(name, destinations)
                    else -> error("Unexpected prefix [$prefix]")
                }
            }
    }

    abstract fun accept(signal: Signal): List<Signal>
}

class Broadcast(name: String, destinations: List<String>) : Module(name, destinations) {
    override fun accept(signal: Signal) =
        destinations.map { Signal(name, it, signal.pulse) }
}

class FlipFlop(name: String, destinations: List<String>) : Module(name, destinations) {
    private var state = false
    override fun accept(signal: Signal) =
        when (signal.pulse) {
            LOW -> if (state) {
                state = false
                destinations.map { Signal(name, it, LOW) }
            } else {
                state = true
                destinations.map { Signal(name, it, HIGH) }
            }

            HIGH -> emptyList()
        }
}

class Conjunction(name: String, destinations: List<String>) : Module(name, destinations) {
    private val memory = mutableMapOf<String, Pulse>()
    override fun accept(signal: Signal): List<Signal> {
        memory[signal.source] = signal.pulse
        return destinations.map { Signal(name, it, if (memory.values.all { it == HIGH }) LOW else HIGH) }
    }

    fun initMemory(inputs: List<String>) =
        memory.putAll(inputs.associateWith { LOW })
}

data class Signal(
    val source: String,
    val target: String,
    val pulse: Pulse,
) {
    override fun toString() =
        "$source -${pulse.name.lowercase()}-> $target"
}

enum class Pulse {
    HIGH, LOW
}