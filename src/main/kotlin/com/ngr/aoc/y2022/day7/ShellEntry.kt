package com.ngr.aoc.y2022.day7

sealed interface ShellEntry {
    companion object {
        fun fromString(line: String) =
            when {
                line.startsWith("$") -> CommandEntry.fromString(line)
                line.startsWith("dir") -> DirEntry.fromString(line)
                else -> FileEntry.fromString(line)
            }

    }
}

data class CommandEntry(
    val command: Command,
    val param: String? = null,
) : ShellEntry {
    companion object {
        fun fromString(line: String): ShellEntry {
            require(line.startsWith("$")) { "line is not a command: $line" }

            return Command.values().first { it.command == line.split(" ")[1] }
                .let { it.converter(line.drop(1).trim()) }
        }
    }
}

class FileEntry(
    val name: String,
    val size: Int,
) : ShellEntry {
    companion object {
        fun fromString(line: String): ShellEntry =
            line.split(" ")
                .let { FileEntry(it[1], it[0].toInt()) }
    }
}

class DirEntry(
    val name: String,
) : ShellEntry {

    companion object {
        fun fromString(line: String): ShellEntry {
            require(line.startsWith("dir")) { "line is not a dir: $line" }

            return DirEntry(line.split(" ")[1])
        }
    }
}