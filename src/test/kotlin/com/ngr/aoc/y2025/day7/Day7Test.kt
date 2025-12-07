package com.ngr.aoc.y2025.day7

import com.ngr.aoc.common.WithDayTest
import com.ngr.aoc.common.WithInputHandling
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day7Test : WithInputHandling, WithDayTest<Day7>(Day7::class) {

    @Nested
    inner class Part2 {
        @Test
        fun test1() {
            val input = """
                .......S.......
                .......^.......
            """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(2)
        }

        @Test
        fun test2() {
            val input = """
                .......S.......
                .......^.......
                ......^.^......
            """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(4)
        }

        @Test
        fun test3() {
            val input = """
                .......S.......
                .......^.......
                ......^........
                ...............
            """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(3)
        }

        @Test
        fun test4() {
            val input = """
                .......S.......
                .......^.......
                ......^........
                .......^.......
                ...............
            """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(4)
        }

        @Test
        fun test5() {
            val input = """
                .......S.......
                ...............
                .......^.......
                ...............
                ......^.^......
                ...............
                .....^.^.^.....
                ...............
                ....^.^...^....
                ...............
                ...^.^...^.^...
                ...............
                ..^...^.....^..
                ...............
                .^.^.^.^.^...^.
                ...............
            """.trimIndent()

            val lines = day.handleInput(input)

            val output = day.part2(lines)

            assertThat(output).isEqualTo(40)
        }
    }
}