package com.ngr.aoc.y2022.day21

data class Monkey(val id: String, val job: Job) {
    companion object {
        private const val HUMAN_ID = "humn"

        private val MONKEY_PATTERN = "(?<id>.+): (((?<left>.+) (?<operator>.+) (?<right>.+))|(?<yell>\\d+))".toRegex()

        fun fromString(line: String): Monkey {
            val matchResult = MONKEY_PATTERN.find(line)!!.groups as MatchNamedGroupCollection

            val id = matchResult["id"]!!.value
            val job = matchResult["yell"]?.let {
                Yell(it.value.toLong())
            } ?: Operation(
                matchResult["left"]!!.value,
                matchResult["right"]!!.value,
                Operator.fromString(matchResult["operator"]!!.value),
            )

            return Monkey(id, job)
        }
    }

    private val adjustabilityMap = mutableMapOf<String, Boolean>()
    var yelled: Long? = null

    fun isAdjustable(monkeys: Map<String, Monkey>): Boolean =
        when (job) {
            is Yell -> id == HUMAN_ID
            is Operation ->
                adjustabilityMap.computeIfAbsent(job.right) { monkeys[job.right]!!.isAdjustable(monkeys) }
                        || adjustabilityMap.computeIfAbsent(job.left) { monkeys[job.left]!!.isAdjustable(monkeys) }
        }

    fun resolveForTarget(target: Long, monkeys: Map<String, Monkey>): Long =
        when (job) {
            is Yell -> target
            is Operation -> {
                if (monkeys[job.left]!!.isAdjustable(monkeys)) {
                    val nextTarget = job.operator.adjustLeft(target, monkeys[job.right]!!.yelled!!)
                    monkeys[job.left]!!.resolveForTarget(nextTarget, monkeys)
                } else {
                    val nextTarget = job.operator.adjustRight(target, monkeys[job.left]!!.yelled!!)
                    monkeys[job.right]!!.resolveForTarget(nextTarget, monkeys)
                }
            }
        }

    fun performJob(monkeys: Map<String, Monkey>) =
        job.perform(monkeys)
            .also { yelled = it }
}