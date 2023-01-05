package com.ngr.aoc.y2022.day21

data class Monkey(val id: String, val job: Job) {
    companion object {
        private val MONKEY_PATTERN = "(?<id>.+): (((?<left>.+) (?<operator>.+) (?<right>.+))|(?<yell>\\d+))".toRegex()

        fun fromString(line: String): Monkey {
            val matchResult = MONKEY_PATTERN.find(line)!!.groups as MatchNamedGroupCollection

            val id = matchResult["id"]!!.value
            val job = matchResult["yell"]?.let {
                Yell(it.value.toLong())
            } ?: Operation(
                matchResult["left"]!!.value,
                matchResult["right"]!!.value,
                Operator.fromString(matchResult["operator"]!!.value).operation,
            )

            return Monkey(id, job)
        }
    }
}