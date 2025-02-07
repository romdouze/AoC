package com.ngr.aoc.y2024.day24

class CircuitFixer(
    private val gates: List<Gate>,
    private val startingValues: Map<String, Boolean>,
) {
    fun findFix(): List<String> {
        val x = startingValues.entries
            .filter { it.key.startsWith("x") }
            .asNumber()
        val y = startingValues.entries
            .filter { it.key.startsWith("y") }
            .asNumber()

        val pairsToSwap = gates.flatMap { g1: Gate ->
            gates.minus(g1).flatMap { g2: Gate ->
                gates.minus(setOf(g1, g2)).flatMap { g3: Gate ->
                    gates.minus(setOf(g1, g2, g3)).flatMap { g4: Gate ->
                        gates.minus(setOf(g1, g2, g3, g4)).flatMap { g5: Gate ->
                            gates.minus(setOf(g1, g2, g3, g4, g5)).flatMap { g6: Gate ->
                                gates.minus(setOf(g1, g2, g3, g4, g5, g6)).flatMap { g7: Gate ->
                                    gates.minus(setOf(g1, g2, g3, g4, g5, g6, g7)).map { g8: Gate ->
                                        listOf(g1 to g2, g3 to g4, g5 to g6, g7 to g8)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.first { pairs ->
            System.err.println(pairs)
            val newGates = gates.toMutableList()
                .apply {
                    removeAll(pairs.map { it.first } + pairs.map { it.second })
                }.apply {
                    val newPairs = pairs.flatMap {
                        val outputA = it.first.output
                        val outputB = it.second.output
                        listOf(it.first.swapOutput(outputB), it.second.swapOutput(outputA))
                    }
                    addAll(newPairs)
                }.toList()

            val output = Circuit(newGates, startingValues)
                .apply { simulate() }
                .finalOutput()

            output == x + y
        }

        return pairsToSwap
            .flatMap { it.toList() }
            .map { it.output }
    }
}

class Circuit(
    gates: List<Gate>,
    startingValues: Map<String, Boolean>,
) {

    private val outputs = startingValues.toMutableMap()
    private val inputs = gates.flatMap {
        listOf(it.input1 to it, it.input2 to it)
    }.groupBy({ it.first }) { it.second }

    fun simulate() {
        val outputsToHandle = ArrayDeque(outputs.keys)

        while (outputsToHandle.isNotEmpty()) {
            val output = outputsToHandle.removeFirst()

            inputs[output]?.forEach {
                val i1 = outputs[it.input1]
                val i2 = outputs[it.input2]
                if (i1 != null && i2 != null) {
                    outputs[it.output] = it.output(i1, i2)
                    outputsToHandle.add(it.output)
                }
            }
        }
    }

    fun finalOutput() =
        outputs.entries
            .filter { it.key.startsWith("z") }
            .asNumber()
}

fun List<Map.Entry<String, Boolean>>.asNumber() =
    sortedBy { it.key }
        .reversed()
        .joinToString("") { if (it.value) "1" else "0" }
        .toLong(radix = 2)

sealed class Gate(
    val input1: String,
    val input2: String,
    val output: String,
) {
    abstract val gateType: GateType

    companion object {
        private val GATE_PATTERN = "(?<input1>\\w+) (?<gate>\\w+) (?<input2>\\w+) -> (?<output>\\w+)".toRegex()

        fun fromString(s: String): Gate {
            val match = GATE_PATTERN.matchEntire(s)!!

            val gateStr = match.groups["gate"]!!.value
            val input1 = match.groups["input1"]!!.value
            val input2 = match.groups["input2"]!!.value
            val output = match.groups["output"]!!.value

            return when (GateType.fromString(gateStr)) {
                GateType.AND -> AndGate(input1, input2, output)
                GateType.OR -> OrGate(input1, input2, output)
                GateType.XOR -> XorGate(input1, input2, output)
            }
        }
    }

    abstract fun output(input1: Boolean, input2: Boolean): Boolean

    fun swapOutput(newOutput: String) =
        fromString(this.toString().replace(output, newOutput))

    override fun toString() =
        "$input1 $gateType $input2 -> $output"
}

class AndGate(
    input1: String,
    input2: String,
    output: String,
) : Gate(
    input1,
    input2,
    output,
) {
    override val gateType = GateType.AND
    override fun output(input1: Boolean, input2: Boolean) = input1 && input2
}

class OrGate(
    input1: String,
    input2: String,
    output: String,
) : Gate(
    input1,
    input2,
    output,
) {
    override val gateType = GateType.OR
    override fun output(input1: Boolean, input2: Boolean) = input1 || input2
}

class XorGate(
    input1: String,
    input2: String,
    output: String,
) : Gate(
    input1,
    input2,
    output,
) {
    override val gateType = GateType.XOR
    override fun output(input1: Boolean, input2: Boolean) = input1 xor input2
}

enum class GateType {
    AND,
    OR,
    XOR;

    companion object {
        fun fromString(s: String) =
            GateType.entries.first { it.name == s }
    }
}


