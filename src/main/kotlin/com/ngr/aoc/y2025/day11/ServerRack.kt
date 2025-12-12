package com.ngr.aoc.y2025.day11

data class Node(
    val name: String,
    val outputs: List<String>,
) {
    companion object {
        fun fromString(str: String) =
            str.split(":").let {
                val input = it[0]
                val output = it[1].trim().split(" ")
                Node(input, output)
            }
    }
}

class Network {
    private val nodes = HashMap<String, Node>()

    fun addNode(node: Node) {
        nodes[node.name] = node
    }

    fun nbPaths(from: String, to: String): Int {
        var nbPaths = 0
        val toVisit = ArrayDeque(listOf(nodes[from]!!))

        while (toVisit.isNotEmpty()) {
            val node = toVisit.removeFirst()
            node.outputs.forEach {
                if (it == to) nbPaths++
                else toVisit.add(nodes[it]!!)
            }
        }

        return nbPaths
    }
}