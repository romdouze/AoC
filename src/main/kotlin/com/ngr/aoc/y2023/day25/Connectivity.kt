package com.ngr.aoc.y2023.day25

import java.util.*

class Graph(
    val wires: Set<Wire>,
) {
    private val connections: Map<String, Set<String>>
    private val wireMap: Map<String, Map<String, Wire>>

    init {
        val connections = mutableMapOf<String, MutableSet<String>>()
        val wireMap = mutableMapOf<String, MutableMap<String, Wire>>()
        wires.forEach {
            if (!connections.containsKey(it.from)) {
                connections[it.from] = mutableSetOf()
            }
            connections[it.from]!!.add(it.to)
            if (!connections.containsKey(it.to)) {
                connections[it.to] = mutableSetOf()
            }
            connections[it.to]!!.add(it.from)

            if (!wireMap.containsKey(it.from)) {
                wireMap[it.from] = mutableMapOf()
            }
            wireMap[it.from]!![it.to] = it
            if (!wireMap.containsKey(it.to)) {
                wireMap[it.to] = mutableMapOf()
            }
            wireMap[it.to]!![it.from] = it
        }
        this.wireMap = wireMap
        this.connections = connections
    }

    val vertices = connections.keys
    val edge = { from: String, to: String ->
        wireMap[from]!![to]
    }

    val edges = { from: String ->
        wireMap[from]!!
    }

    // To input into graphviz
    fun dotNotation() =
        println("graph day25 {\n" +
                wires.joinToString(";\n") { "${it.from} -- ${it.to}" } + "\n}")

    fun connectivity() =
        connectivity(connections)

    private fun connectivity(connections: Map<String, Set<String>>): List<Set<String>> {
        if (connections.isEmpty()) {
            return emptyList()
        }
        val traversed = traverse(connections)
        val remaining = connections.keys - traversed
        return listOf(traversed) + if (remaining.isNotEmpty()) connectivity(connections.filter { it.key in remaining }) else emptySet()
    }

    private fun traverse(connections: Map<String, Set<String>>): Set<String> {
        val start = connections.keys.first()
        val toVisit = ArrayDeque(listOf(start))
        val visited = mutableSetOf<String>()

        while (toVisit.isNotEmpty()) {
            val current = toVisit.removeFirst()
            visited.add(current)
            toVisit.addAll(connections[current]!!.filter { !visited.contains(it) && !toVisit.contains(it) })
        }

        return visited
    }
}

data class Wire(
    val from: String,
    val to: String,
    val weight: Int = 1,
) {
    fun connectsTo(v: String) =
        from == v || to == v

    fun destination(v: String) =
        from.takeIf { to == v } ?: to
}

// 2200s to give the wrong result :( Must be an error somewhere, but highly inefficient anyway
class StoerWagner(
    private val graph: Graph
) {
    fun minCut(): Pair<List<String>, List<String>> {
        val currentPartition = mutableSetOf<String>()
        var currentBestPartition = setOf<String>()
        var currentBestCut: CutOfThePhase? = null

        var g = graph
        while (g.vertices.size > 1) {
            println("Graph size: ${g.vertices.size}")
            val cutOfThePhase = maximumAdjacencySearch(g)

            if (currentBestCut == null || cutOfThePhase.weight < currentBestCut.weight) {
                currentBestCut = cutOfThePhase
                currentBestPartition = currentPartition + cutOfThePhase.t
            }
            currentPartition.add(cutOfThePhase.t)
            g = mergeVerticesFromCut(g, cutOfThePhase)
        }

        return graph.vertices.partition { currentBestPartition.contains(it) }
    }

    private fun maximumAdjacencySearch(graph: Graph): CutOfThePhase {
        val start = graph.vertices.first()
        val found = mutableListOf(start)
        var candidates = weighCandidates(graph.vertices - start, found, graph)
        var lastCutWeight = 0

        while (candidates.isNotEmpty()) {
            val next = candidates.maxBy { it.value }
            found.add(next.key)
            lastCutWeight = next.value
            candidates = weighCandidates(candidates.keys - next.key, found, graph)
        }

        return CutOfThePhase(
            found[found.lastIndex - 1],
            found.last(),
            lastCutWeight,
        )
    }

    private fun weighCandidates(candidates: Set<String>, found: List<String>, graph: Graph) =
        candidates.associateWith { c -> found.sumOf { graph.edge(c, it)?.weight ?: 0 } }

    private fun mergeVerticesFromCut(graph: Graph, cutOfThePhase: CutOfThePhase): Graph {
        val s = cutOfThePhase.s
        val t = cutOfThePhase.t

        return graph.wires.partition {
            it.connectsTo(s) || it.connectsTo(t)
        }.let {
            val toMerge = it.first
            val toKeep = it.second

            val merged = toMerge.filterNot {
                it.connectsTo(s) && it.connectsTo(t)
            }.let {
                val mergedFromS = it.filter { it.connectsTo(s) }
                    .map {
                        val dest = it.destination(s)
                        Wire(s, dest, it.weight + (graph.edge(dest, t)?.weight ?: 0))
                    }

                val mergedFromT = it.filter {
                    it.connectsTo(t) && mergedFromS.none { fs ->
                        fs.connectsTo(s) && fs.connectsTo(
                            it.destination(t)
                        )
                    }
                }
                    .map { Wire(s, it.destination(t), it.weight) }

                mergedFromS + mergedFromT
            }

            Graph((toKeep + merged).toSet())
        }
    }

    data class CutOfThePhase(
        val s: String,
        val t: String,
        val weight: Int,
    )
}