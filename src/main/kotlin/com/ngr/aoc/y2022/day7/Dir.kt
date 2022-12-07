package com.ngr.aoc.y2022.day7

class Dir(name: String, parent: Dir?) : File(name, parent, 0) {

    val files: MutableMap<String, File> = parent?.let { mutableMapOf(".." to it) } ?: mutableMapOf()

    override val size by lazy {
        files.entries
            .filterNot { it.key == ".." }
            .sumOf { it.value.size }
    }
}