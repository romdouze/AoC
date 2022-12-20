package com.ngr.aoc.y2022.day16

import com.ngr.aoc.y2022.Day

class Day16 : Day<String, Int, Int>() {

    private companion object {
        private const val START = "AA"
        private const val MAX_TIME = 30
        private const val MOVE_TIME = 1
        private const val OPEN_TIME = 1
    }

    private val initialNetwork: MutableMap<String, Valve> = mutableMapOf()

    override fun handleLine(lines: MutableList<String>, line: String) {
        Valve.fromString(line)
            .also { initialNetwork[it.name] = it }
    }

    override fun part1(lines: List<String>): Int {
        val network: Network = initialNetwork.entries.associate { it.key to Node(it.value) }
            .plus(
                initialNetwork.entries
                    .filter { it.value.rate > 0 }
                    .associate { it.key.asOpen() to Node(it.value, open = true) }
            )

        val allPaths = mutableListOf<Path>()
        network.generatePaths(
            MAX_TIME,
            Path(0, listOf(START)),
            network.values.filter { it.open }.map { it.name },
            allPaths
        )

        val bestPath = allPaths.maxBy { network.released(it, MAX_TIME) }

//        var currentNode = network[START]
//            ?.apply {
//                elapsed = 0
//                released = 0
//                path = Path(0, listOf(this.name))
//            }
//
//        val toVisit = network.allOpenable.toMutableList()
//
//        while (currentNode != null && network.values.any { it.elapsed < MAX_TIME }) {
//
//            val remainingTime = MAX_TIME - currentNode.elapsed
//
//            println("time: $remainingTime, currentNode: $currentNode")
//
//            toVisit
//                .filter { currentNode!!.name != it.name }
//                .onEach { nextToVisit ->
//                    val path = network.pathTo(currentNode!!.name, nextToVisit.name)
//                    val tentativeReleased =
//                        currentNode!!.released + nextToVisit.valve.rate * (remainingTime - path.elapsed)
//                    val tentativeElapsed = currentNode!!.elapsed + path.elapsed
//                    if (tentativeElapsed <= MAX_TIME && tentativeReleased > nextToVisit.released) {
//                        nextToVisit.released = tentativeReleased
//                        nextToVisit.elapsed = tentativeElapsed
//                        nextToVisit.path = currentNode!!.path + path
//                    }
//                }
//
//            toVisit.remove(currentNode)
//            currentNode = toVisit
//                .filter { it.elapsed < MAX_TIME }
//                .maxByOrNull { it.released }
//        }

//        val toVisit = network.allOpenable.toMutableList()
//        var bestPath = Path(0, listOf(START))
//        var finished = false
//
//        while (toVisit.isNotEmpty() && !finished) {
//            val next = toVisit
//                .filter { next ->
//                    bestPath.elapsed + network.pathTo(bestPath.path.last(), next.name).elapsed <= MAX_TIME
//                }
//                .maxByOrNull { next ->
//                    (MAX_TIME - network.pathTo(bestPath.path.last(), next.name).elapsed) * next.valve.rate
//                }
//
//            if (next != null) {
//                bestPath += network.pathTo(bestPath.path.last(), next.name)
//                toVisit.remove(next)
//            } else finished = true
//        }

        return network.released(bestPath, MAX_TIME)
        //        return network.values.maxOf { it.released }
    }

    override fun part2(lines: List<String>): Int {
        TODO("Not yet implemented")
    }

    private fun Network.generatePaths(
        maxTime: Int,
        currentPath: Path,
        availableNodes: List<String>,
        allPaths: MutableList<Path>,
    ) {
        availableNodes
            .map { currentPath + pathTo(currentPath.end, it) }
            .forEach { next ->
                val currentBest = allPaths.maxOfOrNull { released(it, MAX_TIME) } ?: Int.MIN_VALUE
                val available = availableNodes.minus(next.end)
                    .filter { (next + pathTo(next.end, it)).elapsed <= MAX_TIME }
                if (available.isNotEmpty()) {
                    if (bestPotential(next, available, maxTime) > currentBest) {
                        generatePaths(maxTime, next, available, allPaths)
                    }
                } else {
                    if (released(next, maxTime) > currentBest) {
                        println("adding path: $next")
                        allPaths.add(next)
                    }
                }
            }
    }

    private fun Network.bestPotential(partialPath: Path, availableNodes: List<String>, maxTime: Int): Int {
        val minTime = if (availableNodes.isEmpty()) 0 else availableNodes.minOf { pathTo(partialPath.end, it).elapsed }
        return released(partialPath, maxTime) + availableNodes.mapNotNull { this[it] }
            .sumOf { it.valve.rate * (maxTime - partialPath.elapsed - minTime) }
    }
}