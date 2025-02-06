package com.ngr.aoc.y2024.day23

class LanParty(
    connections: List<Pair<String, String>>
) {
    private val network: Map<String, List<String>>

    init {
        val network = mutableMapOf<String, MutableList<String>>()

        connections.forEach {
            network.computeIfAbsent(it.first) { mutableListOf() }.add(it.second)
        }

        this.network = network
    }

    fun findTriples(): Set<List<String>> {
        val triples = mutableSetOf<List<String>>()

        network.keys.forEach { first ->
            network[first]!!.let { connectionsOfFirst ->
                connectionsOfFirst.flatMapIndexed { i, second ->
                    connectionsOfFirst.subList(i + 1, connectionsOfFirst.size).map { second to it }
                }
            }.filter { (second, third) ->
                network[second]!!.contains(third)
            }.forEach { (second, third) ->
                triples.add(listOf(first, second, third).sorted())
            }
        }

        return triples
    }

    fun findLanParty(): List<String> {
        network.keys.
    }
}