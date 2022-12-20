package com.ngr.aoc.y2022.day16

typealias Path = Pair<Int, List<String>>

val Path.elapsed get() = first
val Path.path get() = second
val Path.start get() = path.first()
val Path.end get() = path.last()

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
    pathCache.computeIfAbsent(start to end) {
        computePath(start, end)
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