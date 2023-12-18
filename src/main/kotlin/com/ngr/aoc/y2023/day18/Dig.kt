package com.ngr.aoc.y2023.day18

data class Instruction(
    val dir: Dir,
    val distance: Long,
    val color: String,
) {
    val trueDir = Dir.fromColor(color.last())
    val trueDistance = color.take(5).toLong(16)

    companion object {

        private val INSTRUCTION_PATTERN = "(?<dir>\\w) (?<distance>\\d+) \\(#(?<color>.+)\\)".toRegex()

        fun fromString(string: String) =
            INSTRUCTION_PATTERN.find(string)!!.groups.let {
                Instruction(
                    Dir.fromString(it["dir"]!!.value),
                    it["distance"]!!.value.toLong(),
                    it["color"]!!.value,
                )
            }
    }
}

enum class Dir(
    val dx: Int,
    val dy: Int,
    val char: Char,
) {
    U(0, -1, '3'),
    D(0, 1, '1'),
    R(1, 0, '0'),
    L(-1, 0, '2');

    companion object {
        fun fromString(string: String) =
            Dir.values().first { it.name == string }

        fun fromColor(char: Char) =
            Dir.values().first { it.char == char }
    }
}

data class PointL(
    val x: Long,
    val y: Long,
)

fun PointL.plus(dir: Dir, distance: Long) =
    PointL(x + dir.dx * distance, y + dir.dy * distance)