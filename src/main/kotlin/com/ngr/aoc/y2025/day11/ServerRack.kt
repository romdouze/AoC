package com.ngr.aoc.y2025.day11

import java.io.File

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

    fun nbPaths(from: String, to: String, stopNodes: List<String> = emptyList()): Int {
        var nbPaths = 0
        val toVisit = ArrayDeque(listOf(nodes[from]!!))

        while (toVisit.isNotEmpty()) {
            val node = toVisit.removeFirst()
            if (!stopNodes.contains(node.name)) {
                node.outputs.forEach {
                    if (it == to) nbPaths++
                    else toVisit.add(nodes[it]!!)
                }
            }
        }

        return nbPaths
    }

    fun asGraphviz() {
        File("src/main/resources/output/2025/11/graphviz.txt")
            .bufferedWriter().use { out ->
                nodes.values.forEach { node ->
                    node.outputs.forEach {
                        out.write("${node.name} -> ${it}\n")
                    }
                }
            }
    }
}