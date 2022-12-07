package com.ngr.aoc.y2022.day7

import com.ngr.aoc.y2022.Day

class Day7 : Day<ShellEntry, Int, Int>() {

    companion object {
        private const val TOTAL_SPACE = 70000000
        private const val REQUIRED_SPACE = 30000000
    }

    private val root = Dir("/", null)
    private val dirs = mutableSetOf<Dir>()

    override fun handleLine(lines: MutableList<ShellEntry>, line: String) {
        lines.add(ShellEntry.fromString(line))
    }

    override fun part1(lines: List<ShellEntry>): Int {
        buildTree(lines)
        return dirs
            .filter { it.size <= 100000 }
            .sumOf { it.size }
    }

    override fun part2(lines: List<ShellEntry>): Int {
        buildTree(lines)
        val missingSpace = REQUIRED_SPACE - (TOTAL_SPACE - root.size)
        return dirs
            .filter { it.size >= missingSpace }
            .minBy { it.size }
            .size
    }

    private fun buildTree(entries: List<ShellEntry>) {
        var currentDir = root
        entries.drop(1) // Yes, we drop the first instruction which is $ cd / and is annoying to account for.
            .forEach { entry ->
                when (entry) {
                    is DirEntry,
                    is FileEntry ->
                        File.fromShellEntry(entry, currentDir).also {
                            currentDir.files[it.name] = it
                            if (it is Dir) dirs.add(it)
                        }

                    is CommandEntry ->
                        if (entry.command == Command.CD) {
                            currentDir = currentDir.files[entry.param] as? Dir
                                ?: throw IllegalArgumentException("Cannot cd to a file that is not a dir: ${entry.param}")
                        }
                }
            }
    }
}