package com.ngr.aoc.y2023.day8

import com.ngr.aoc.Day
import com.ngr.aoc.common.lcm

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

        val cacheMap = lines.filter { it.id.endsWith("A") }
            .associate { it.id to buildCache(it.id, nodeMap) }

        var steps = 0L

//        var currentNodes = nodeMap.values.filter { it.id.endsWith("A") }
//        var instructionIndex = 0
//        var currentInstruction = instructions[instructionIndex]
//        var steps = 0L
//
//        while (currentNodes.any { !it.id.endsWith("Z") }) {
//            currentNodes = currentNodes.map {
//                when (currentInstruction) {
//                    'R' -> nodeMap[it.right]!!
//                    'L' -> nodeMap[it.left]!!
//                    else -> throw IllegalArgumentException("Invalid instruction [$currentInstruction]")
//                }
//            }
//            instructionIndex = (instructionIndex + 1) % instructions.length
//            currentInstruction = instructions[instructionIndex]
//            steps++
//            if (steps % 1000000L == 0L){
//                println(steps)
//            }
//        }

        return lcm(*cacheMap.values.map { it.first.size - it.second }.toIntArray()).toLong()
    }

    private fun buildCache(node: String, nodeMap: Map<String, Node>): Pair<Set<Pair<String, Int>>, Int> {
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

        return cache to cache.indexOf(currentNode.id to instructionIndex)
    }
}