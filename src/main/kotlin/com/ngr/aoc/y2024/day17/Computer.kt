package com.ngr.aoc.y2024.day17

import com.ngr.aoc.y2024.day17.OperandType.COMBO
import com.ngr.aoc.y2024.day17.OperandType.LITERAL
import kotlin.math.pow

class CloneFinder {
    fun findLowestAForClone(program: List<Int>) =
        findMatchesForDigit(0L, program.lastIndex, program).min()

    private fun findMatchesForDigit(prefix: Long, digitId: Int, program: List<Int>): List<Long> {
        if (digitId < 0) {
            return listOf(prefix)
        }
        val digit = program[digitId].toLong()
        val baseA = prefix * 8
        return (baseA until baseA + 8)
            .associateWith {
                Computer(it, 0, 0).run(program)
            }.filter {
                it.value.output.first() == digit
            }.flatMap {
                findMatchesForDigit(it.key, digitId - 1, program)
            }
    }

    /*

    2,4,1,1,7,5,0,3,1,4,4,0,5,5,3,0

    (2,4): b = a % 8       => b = a % 8
    (1,1): b = b X 1       => b = bit 1 toggled
    (7,5): c = a / 2^b     => c = a >> b
    (0,3): a = a / 2^3     => a = a >> 3
    (1,4): b = b X 4       => b = bit 3 toggled
    (4,0): b = b X c       => b =
    (5,5): output <- b%8   => output += b%8
    (3,0): jnz 0           => loop to start if a != 0


     0   3   5   5   0   4   4   1   3   0   5   7   1   1   4   2
    ... ...
    101 110 000 000 000 000 000 000 000 000 000 000 000 000 000 000

    */
}

data class Computer(
    var a: Long,
    var b: Long,
    var c: Long,
) {
    var instructionPointer = 0

    val output = mutableListOf<Long>()

    fun run(program: List<Int>): Computer {

        while (program.indices.contains(instructionPointer)) {
            val instruction = Instruction.fromOpcode(program[instructionPointer])
            val resolvedOperand = instruction.operandType.resolver(this, program[++instructionPointer])
            instruction.operation(this, resolvedOperand)
            instructionPointer++
        }

        return this
    }
}

enum class Instruction(val opcode: Int, val operandType: OperandType, val operation: Computer.(operand: Long) -> Unit) {
    ADV(0, COMBO, {
        a = (a / 2.0.pow(it.toDouble())).toLong()
    }),
    BXL(1, LITERAL, {
        b = b xor it
    }),
    BST(2, COMBO, {
        b = it % 8
    }),
    JNZ(3, LITERAL, {
        if (a != 0L) {
            instructionPointer = it.toInt() - 1
        }
    }),
    BXC(4, LITERAL, {
        b = b xor c
    }),
    OUT(5, COMBO, {
        output.add(it % 8)
    }),
    BDV(6, COMBO, {
        b = (a / 2.0.pow(it.toDouble())).toLong()
    }),
    CDV(7, COMBO, {
        c = (a / 2.0.pow(it.toDouble())).toLong()
    });

    companion object {
        fun fromOpcode(opcode: Int) =
            entries.first { it.opcode == opcode }
    }
}

enum class OperandType(val resolver: Computer.(operand: Int) -> Long) {
    LITERAL({ it.toLong() }),
    COMBO({
        when (it) {
            4 -> a
            5 -> b
            6 -> c
            else -> it.toLong()
        }
    }),
}