package com.ngr.aoc.y2023.day8

import com.ngr.aoc.Day

class Day8 : Day<Node, Int, Long>() {

    private companion object {
        private val NODE_PATTERN = "(?<id>\\w+) = \\((?<left>\\w+), (?<right>\\w+)\\)".toRegex()
    }

    private lateinit var instructions: String

    override fun handleLine(lines: MutableList<Node>, line: String) {
        if (line.isNotBlank() && !line.contains("=")) {
            instructions = line
        } else if (line.isNotBlank()) {
            val groups = NODE_PATTERN.find(line)!!.groups

            lines.add(
                Node(
                    groups["id"]!!.value,
                    groups["left"]!!.value,
                    groups["right"]!!.value,
                )
            )
        }
    }

    override fun part1(lines: List<Node>): Int {
        val nodeMap = lines.associateBy { it.id }

        var currentNode = nodeMap["AAA"]!!
        var instructionIndex = 0
        var currentInstruction = instructions[instructionIndex]
        var steps = 0

        while (currentNode.id != "ZZZ") {
            currentNode = when (currentInstruction) {
                'R' -> nodeMap[currentNode.right]!!
                'L' -> nodeMap[currentNode.left]!!
                else -> throw IllegalArgumentException("Invalid instruction [$currentInstruction]")
            }
            instructionIndex = (instructionIndex + 1) % instructions.length
            currentInstruction = instructions[instructionIndex]
            steps++
        }

        return steps
    }

    override fun part2(lines: List<Node>): Long {
        val nodeMap = lines.associateBy { it.id }

        val loopMap = lines.filter { it.id.endsWith("A") }
            .map { buildLoop(it.id, nodeMap) }

        val furthestEndpoint = loopMap.maxBy { it.endpoints.max() }
        val targetIndex = furthestEndpoint.endpoints.max()

        var loopCount = 1
        var currentIndex = targetIndex.toLong()

        while (loopMap.any {
                !it.endpoints.contains(
                    ((currentIndex - it.offset) % it.length + it.offset).toInt()
                )
            }) {

            loopCount++
            currentIndex += furthestEndpoint.length
        }

        return currentIndex
    }

    private fun buildLoop(node: String, nodeMap: Map<String, Node>): Loop {
        var currentNode = nodeMap[node]!!
        var instructionIndex = 0
        var currentInstruction = instructions[instructionIndex]
        var steps = 0L
        val cache = mutableSetOf<Pair<String, Int>>()

        while (!cache.contains(currentNode.id to instructionIndex)) {
            cache.add(currentNode.id to instructionIndex)
            currentNode = when (currentInstruction) {
                'R' -> nodeMap[currentNode.right]!!
                'L' -> nodeMap[currentNode.left]!!
                else -> throw IllegalArgumentException("Invalid instruction [$currentInstruction]")
            }
            instructionIndex = (instructionIndex + 1) % instructions.length
            currentInstruction = instructions[instructionIndex]
            steps++
        }
        val cacheList = cache.toList()
        val loopOffset = cacheList.indexOf(currentNode.id to instructionIndex)

        return Loop(
            offset = loopOffset,
            length = cache.size - loopOffset,
            endpoints = cache.toList()
                .mapIndexed { index, pair ->
                    pair.first to index
                }.filter { it.first.endsWith("Z") }
                .map { it.second },
        )
    }
}