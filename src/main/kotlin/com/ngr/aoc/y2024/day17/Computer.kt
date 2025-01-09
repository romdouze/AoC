package com.ngr.aoc.y2024.day17

import com.ngr.aoc.y2024.day17.Instruction.entries
import com.ngr.aoc.y2024.day17.OperandType.COMBO
import com.ngr.aoc.y2024.day17.OperandType.LITERAL
import kotlin.math.pow

class CloneFinder(val computer: Computer) {
    fun findClone(program: List<Int>): Int {
        val programStr = program.joinToString(",")

        val outputMap = (0 until 64).associateWith {
            computer.copy(it, 0, 0)
                .run(program)
                .output[0]
        }

        var a = 0
//        var newComputer = computer.copy(a = a)
//
//        while (newComputer.run(program) != programStr) {
//            a++
//            newComputer = computer.copy(a = a)
//        }
        return a
    }
}

data class Computer(
    var a: Int,
    var b: Int,
    var c: Int,
) {
    var instructionPointer = 0

    val output = mutableListOf<Int>()

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

enum class Instruction(val opcode: Int, val operandType: OperandType, val operation: Computer.(operand: Int) -> Unit) {
    ADV(0, COMBO, {
        a = (a / 2.0.pow(it)).toInt()
    }),
    BXL(1, LITERAL, {
        b = b.xor(it)
    }),
    BST(2, COMBO, {
        b = it % 8
    }),
    JNZ(3, LITERAL, {
        if (a != 0) {
            instructionPointer = it - 1
        }
    }),
    BXC(4, LITERAL, {
        b = b.xor(c)
    }),
    OUT(5, COMBO, {
        output.add(it % 8)
    }),
    BDV(6, COMBO, {
        b = (a / 2.0.pow(it)).toInt()
    }),
    CDV(7, COMBO, {
        c = (a / 2.0.pow(it)).toInt()
    });

    companion object {
        fun fromOpcode(opcode: Int) =
            entries.first { it.opcode == opcode }
    }
}

enum class OperandType(val resolver: Computer.(operand: Int) -> Int) {
    LITERAL({ it }),
    COMBO({
        when (it) {
            4 -> a
            5 -> b
            6 -> c
            else -> it
        }
    }),
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
                                        ... ... ... ...
000 000 000 000 000 000 000 000 000 000 000 010 101 000 000 000


 */