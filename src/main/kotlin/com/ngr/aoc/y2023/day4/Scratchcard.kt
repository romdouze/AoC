package com.ngr.aoc.y2023.day4

data class Scratchcard(
    val id: Int,
    val winning: Set<Int>,
    val selected: Set<Int>,
) {
    companion object {

        private val CARD_ID_PATTERN = "Card\\s+(?<id>\\d+):".toRegex()

        fun fromString(line: String): Scratchcard {
            val id = CARD_ID_PATTERN.find(line)!!.groups["id"]!!.value.toInt() - 1

            return line.substring(line.indexOf(":") + 1)
                .split("|")
                .map {
                    it.trim()
                        .split(" ")
                        .filter { it.isNotEmpty() }
                        .map { it.trim().toInt() }
                }.let {
                    Scratchcard(
                        id,
                        it[0].toSet(), it[1].toSet()
                    )
                }
        }
    }

    val matching = winning.intersect(selected)
}