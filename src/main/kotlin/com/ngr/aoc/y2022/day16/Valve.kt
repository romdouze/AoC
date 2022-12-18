package com.ngr.aoc.y2022.day16

data class Valve(
    val name: String,
    val rate: Int,
    val tunnels: List<String>,
) {
    companion object {
        private val VALVE_PATTERN =
            "Valve (?<name>\\w+) has flow rate=(?<rate>\\d+); tunnels lead to valves (?<tunnels>.+)".toRegex()

        fun fromString(line: String) =
            VALVE_PATTERN.find(line)
                .let {
                    val (name, rate, tunnels) = it!!.destructured
                    Valve(name, rate.toInt(), tunnels.split(", "))
                }
    }
}