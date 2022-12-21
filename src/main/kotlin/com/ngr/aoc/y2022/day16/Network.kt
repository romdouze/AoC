package com.ngr.aoc.y2022.day16

import java.lang.RuntimeException

typealias Path = Pair<Int, List<String>>

val Path.elapsed get() = first
val Path.path get() = second
val Path.start get() = path.first()
val Path.end get() = path.last()

fun emptyPath(start: String) = Path(0, listOf(start))

operator fun Path.plus(other: Path) =
    Path(elapsed + other.elapsed, path.dropLast(1) + other.path)

fun Path.withoutLastOpened(): Path {
    val mutablePath = path.dropLast(1).toMutableList()
    while (!mutablePath.last().isOpen() || mutablePath.last()
            .isOpen() && mutablePath.last() != mutablePath[mutablePath.size - 2].asOpen()
    ) {
        mutablePath.removeLast()
    }

    return Path(mutablePath.size, mutablePath)
}


typealias Network = Map<String, Node>

private val pathCache: MutableMap<Pair<String, String>, Path> = mutableMapOf()

val Network.allOpenable get() = values.filter { it.open }

fun Network.released(path: Path, remainingTimeAtStart: Int) =
    allOpenable
        .filter { path.path.contains(it.name) }
        .sumOf { node ->
            (remainingTimeAtStart - path.path.indexOfFirst { node.name == it }) * node.valve.rate
        }

fun Network.pathTo(start: String, end: String) =
    if (end == NONE) {
        emptyPath(start)
    } else {
        pathCache.computeIfAbsent(start to end) {
            computePath(start, end)
        }
    }

private fun Network.computePath(start: String, end: String): Path {
    val queue = ArrayDeque(listOf(start))

    val flood = this.keys
        .associateWith { Pair(Int.MAX_VALUE, emptyList<String>()) }
        .toMutableMap()

    flood[start] = 0 to listOf(start)
    while (queue.isNotEmpty() && flood[end]!!.first == Int.MAX_VALUE) {
        val currentNode = queue.removeFirst()
        val dist = flood[currentNode]!!.first

        this[currentNode]!!.neighbours(this)
            .map { it.name }
            .filter {
                !queue.contains(it) && flood[it]!!.first > dist
            }
            .forEach {
                flood[it] = dist + 1 to flood[currentNode]!!.second + it
                queue.addLast(it)
            }
    }

    return flood[end]!!
}

private const val NONE = "NONE"

fun Network.generateMultiPath(
    maxTime: Int,
    currentMultiPath: Pair<Path, Path>,
    availableNodes: Pair<List<String>, List<String>>,
    allPaths: MutableList<Pair<Path, Path>>
) {
    availableNodes.first
        .map {
            it to
                    currentMultiPath.first + pathTo(currentMultiPath.first.end, it)
        }
        .ifEmpty { listOf(NONE to currentMultiPath.first) }
        .forEach { first ->
            val (firstPick, firstNext) = first
            availableNodes.second
                .minus(firstPick)
                .map {
                    it to
                            currentMultiPath.second + pathTo(currentMultiPath.second.end, it)
                }
                .ifEmpty { listOf(NONE to currentMultiPath.second) }
                .forEach { second ->
                    val (secondPick, secondNext) = second
                    val currentBest =
                        allPaths.maxOfOrNull { it.toList().sumOf { released(it, maxTime) } } ?: Int.MIN_VALUE
                    val available =
                        availableNodes.first.minus(listOf(firstPick, secondPick).toSet())
                            .filter {
                                firstPick == NONE || (firstNext + pathTo(firstPick, it)).elapsed <= maxTime
                            } to
                                availableNodes.second.minus(setOf(firstPick, secondPick))
                                    .filter {
                                        secondPick == NONE || (secondNext + pathTo(secondPick, it)).elapsed <= maxTime
                                    }
                    if (available.toList().any { it.isNotEmpty() }) {
                        if (listOf(firstNext to available.first, secondNext to available.second)
                                .sumOf { bestPotential(it.first, it.second, maxTime) } > currentBest
                        ) {
                            generateMultiPath(maxTime, firstNext to secondNext, available, allPaths)
                        }
                    } else {
                        if (listOf(firstNext, secondNext).sumOf { released(it, maxTime) } > currentBest) {
                            println(
                                "\nadding multiPath:\n\t$firstNext\n\t$secondNext\n=>${
                                    listOf(
                                        firstNext,
                                        secondNext
                                    ).sumOf { released(it, maxTime) }
                                }"
                            )
                            allPaths.add(firstNext to secondNext)
                        }
                    }
                }
        }
}

fun Network.generatePaths(
    maxTime: Int,
    currentPath: Path,
    availableNodes: List<String>,
    allPaths: MutableList<Path>,
) {
    availableNodes
        .map { currentPath + pathTo(currentPath.end, it) }
        .forEach { next ->
            val currentBest = allPaths.maxOfOrNull { released(it, maxTime) } ?: Int.MIN_VALUE
            val available = availableNodes.minus(next.end)
                .filter { (next + pathTo(next.end, it)).elapsed <= maxTime }
            if (available.isNotEmpty()) {
                if (bestPotential(next, available, maxTime) > currentBest) {
                    generatePaths(maxTime, next, available, allPaths)
                }
            } else {
                if (released(next, maxTime) > currentBest) {
//                    println("adding path: $next")
                    allPaths.add(next)
                }
            }
        }
}

fun Network.bestPotential(partialPath: Path, availableNodes: List<String>, maxTime: Int): Int {
    val minTime = if (availableNodes.isEmpty()) 0 else availableNodes.minOf { pathTo(partialPath.end, it).elapsed }
    return released(partialPath, maxTime) + availableNodes.mapNotNull { this[it] }
        .sumOf { it.valve.rate * (maxTime - partialPath.elapsed - minTime) }
}