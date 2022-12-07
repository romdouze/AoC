package com.ngr.aoc.y2022.day7

enum class Command(
    val command: String,
    val converter: (String) -> CommandEntry
) {
    CD("cd", { CommandEntry(CD, it.split(" ")[1]) }),
    LS("ls", { CommandEntry(LS) })
}