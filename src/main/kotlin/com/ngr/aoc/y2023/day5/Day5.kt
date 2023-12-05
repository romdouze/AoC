package com.ngr.aoc.y2023.day5

import com.ngr.aoc.Day
import java.io.InputStream

class Day5 : Day<Converter, Long, Long>() {

    private companion object {
        private const val SEEDS_PREFIX = "seeds: "
        private val MAP_NAME_PATTERN = "(?<source>\\w+)-to-(?<destination>\\w+) map:".toRegex()
    }

    private val seeds = mutableSetOf<Long>()

    override fun readInput(data: InputStream): List<Converter> {
        val converters = mutableListOf<Converter>()

        data.bufferedReader().let { reader ->
            val seedsLine = reader.readLine()
            seedsLine.removePrefix(SEEDS_PREFIX)
                .split(" ")
                .map { it.toLong() }
                .also { seeds.addAll(it) }

            reader.readLine()

            var mapNameLine = reader.readLine()
            do {
                val groups = MAP_NAME_PATTERN.find(mapNameLine)!!.groups
                val source = Category.fromString(groups["source"]!!.value)
                val destination = Category.fromString(groups["destination"]!!.value)
                val map = mutableListOf<Pair<LongRange, LongRange>>()

                var line = reader.readLine()
                do {
                    line.split(" ")
                        .map { it.toLong() }
                        .also { map.add(LongRange(it[1], it[1] + it[2] - 1) to LongRange(it[0], it[0] + it[2] - 1)) }

                    line = reader.readLine()
                } while (!line.isNullOrBlank())

                converters.add(
                    Converter(source, destination, map)
                )

                mapNameLine = reader.readLine()
            } while (!mapNameLine.isNullOrBlank())
        }

        return converters
    }

    override fun handleLine(lines: MutableList<Converter>, line: String) {
        // DO NOTHING
    }

    override fun part1(lines: List<Converter>): Long {
        val converterMap = lines.associateBy { it.source }

        return seeds.minOf { seed ->
            var converter: Converter? = converterMap[Category.SEED]!!
            var number = seed

            do {
                number = converter!!.convert(number)
                converter = converterMap[converter.destination]
            } while (converter != null)

            number
        }
    }

    override fun part2(lines: List<Converter>): Long {
        val converterMap = lines.associateBy { it.source }

        return seeds.chunked(2)
            .map { it[0] to it[1] - 1 }
            .minOf { seedRange ->

                var converter = converterMap[Category.SEED]
                var chunkedRange = listOf(seedRange)

                do {
                    chunkedRange = chunkedRange.flatMap {
                        converter!!.convert(it)
                    }
                    converter = converterMap[converter!!.destination]
                } while (converter != null)

                chunkedRange.minOf { it.first }
            }
    }
}