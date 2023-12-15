package com.ngr.aoc.y2023.day15

import com.ngr.aoc.Day

class Day15 : Day<String, Int, Int>() {

    private companion object {
        private val INSTRUCTION_PATTERN = "(?<label>\\w+)(((?<add>=)(?<focal>\\d))|(?<remove>-))".toRegex()
    }

    override fun handleLine(lines: MutableList<String>, line: String) {
        lines.addAll(line.split(","))
    }

    override fun part1(lines: List<String>) =
        lines.sumOf { it.hash() }

    override fun part2(lines: List<String>): Int {
        val boxes = mutableMapOf<Int, MutableList<Lens>>()
        lines.forEach { instruction ->
            val groups = INSTRUCTION_PATTERN.find(instruction)!!.groups
            val label = groups["label"]!!.value

            val hash = label.hash()
            when {
                groups["add"] != null -> {
                    val focal = groups["focal"]!!.value.toInt()
                    if (!boxes.containsKey(hash)) {
                        boxes[hash] = mutableListOf()
                    }
                    boxes[hash]!!.firstOrNull { it.label == label }?.apply { this.focal = focal }
                        ?: boxes[hash]!!.add(Lens(label, focal))
                }

                groups["remove"] != null -> boxes[hash]?.removeIf { it.label == label }
            }
        }

        return boxes.entries.sumOf {
            (it.key + 1) * it.value.foldIndexed(0) { index, acc, lens ->
                acc + (index + 1) * lens.focal
            }
        }
    }
}