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

//        val allPaths = mutableListOf<Path>()
//        network.generatePaths(
//            MAX_TIME,
//            Path(0, listOf(START)),
//            network.values.filter { it.open }.map { it.name },
//            allPaths
//        )

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

        val toVisit = network.allOpenable.toMutableList()
        var path = Path(0, listOf(START))
        var finished = false

        while (toVisit.isNotEmpty() && !finished) {
            val next = toVisit
                .filter { next ->
                    path.elapsed + network.pathTo(path.path.last(), next.name).elapsed <= MAX_TIME
                }
                .maxByOrNull { next ->
                    (MAX_TIME - network.pathTo(path.path.last(), next.name).elapsed) * next.valve.rate
                }

            if (next != null) {
                path += network.pathTo(path.path.last(), next.name)
                toVisit.remove(next)
            } else finished = true
        }

        return path.elapsed
        //        return network.values.maxOf { it.released }
    }

    override fun part2(lines: List<String>): Int {
        TODO("Not yet implemented")
    }

    private fun Network.generatePaths(
        maxTime: Int,
        currentPath: Path,
        available: List<String>,
        allPaths: MutableList<Path>
    ) {
        if (available.isEmpty()) {
            allPaths.add(currentPath)
        } else {
            val (unfinished, finished) = available.map {
                pathTo(currentPath.path.last(), it)
            }.map {
                currentPath + it
            }.partition {
                it.elapsed <= maxTime
            }
            allPaths.addAll(finished)
            unfinished.forEach {
                generatePaths(maxTime, it, available.minus(it.path.last()), allPaths)
            }
        }
    }

}