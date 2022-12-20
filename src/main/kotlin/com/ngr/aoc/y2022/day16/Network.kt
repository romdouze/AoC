package com.ngr.aoc.y2022.day16

typealias Network = Map<String, Node>
typealias Path = Pair<Int, List<String>>

val Path.elapsed get() = first
val Path.path get() = second

operator fun Path.plus(other: Path) =
    Path(elapsed + other.elapsed, path.dropLast(1) + other.path)

private val pathCache: MutableMap<Pair<String, String>, Path> = mutableMapOf()

val Network.allOpenable get() = values.filter { it.open }

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