package com.ngr.aoc.y2022.day16

data class Node(
    val valve: Valve,
    var open: Boolean = false,
    var elapsed: Int = Int.MIN_VALUE,
    var released: Int = Int.MIN_VALUE,
    var path: Path = Path(0, emptyList()),
) {
    val name = if (open) valve.name.asOpen() else valve.name

    fun neighbours(network: Map<String, Node>): Set<Node> =
        network.values.filter { node ->
            val openedNeighbours = this.valve.tunnels.filter { path.path.contains(it.asOpen()) }.toSet()
            node.name in this.valve.tunnels
                .plus(this.name.asOpen())
                .plus(openedNeighbours.map { it.asOpen() })
                .minus(openedNeighbours)
        }.toSet()

    override fun toString() =
        "Node(name=${name}, valve=${valve}, open=$open, elapsed=$elapsed, released=$released, path=${path})"
}