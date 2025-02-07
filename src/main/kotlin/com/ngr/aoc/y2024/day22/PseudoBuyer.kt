package com.ngr.aoc.y2024.day22

class PseudoHaggler(
    private val buyers: List<PseudoBuyer>,
) {
    fun maximizeBananas(): Long {
        return buyers.flatMap { it.variationSequences.keys }
            .toSet()
            .maxOf { sequence ->
                buyers.sumOf { it.variationSequences[sequence]?.toLong() ?: 0L }
            }
    }
}

class PseudoBuyer(
    initialSecret: Long,
) {
    private val secrets: List<Long>
    private val prices: List<Int>
    val variationSequences: Map<List<Int>, Int>

    init {
        val secrets = mutableListOf(initialSecret)
        val prices = mutableListOf(initialSecret.price())
        val variationSequences = mutableMapOf<List<Int>, Int>()
        val last4Variations = ArrayDeque<Int>()
        repeat(NB_SECRETS) {
            val nextSecret = generateNextSecret(secrets.last())
            secrets.add(nextSecret)
            val price = nextSecret.price()
            val previousPrice = prices.last()
            prices.add(price)
            last4Variations.add(price - previousPrice)
            if (last4Variations.size >= 4) {
                variationSequences.computeIfAbsent(last4Variations.toList()) { prices.last() }
                last4Variations.removeFirst()
            }
        }

        this.secrets = secrets
        this.prices = prices
        this.variationSequences = variationSequences
    }

    fun getNthSecret(n: Int) = secrets[n]

    private fun generateNextSecret(secret: Long): Long {
        var nextSecret = secret.mixWith(secret * 64).prune()
        nextSecret = nextSecret.mixWith(nextSecret / 32).prune()
        nextSecret = nextSecret.mixWith(nextSecret * 2048).prune()

        return nextSecret
    }

    private fun Long.price() =
        this.toString().last().digitToInt()

    private fun Long.mixWith(other: Long) =
        this xor other

    private fun Long.prune() =
        this % 16777216
}