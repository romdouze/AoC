package com.ngr.aoc.y2022.day13

class Packet(
    line: String
) {
    val items = line.replace("10", "T")

    operator fun compareTo(other: Packet) = items.compareNesting(other.items)
}

fun Pair<Packet, Packet>.compare() = first.items.compareNesting(second.items)

fun String.compareNesting(other: String): Int {
    if (this.isEmpty() && other.isEmpty()) return 0
    if (this.isEmpty() && other.isNotEmpty()) return -1
    if (this.isNotEmpty() && other.isEmpty()) return 1

    val f = this[0]
    val s = other[0]
    return if (f == s) {
        this.substring(1).compareNesting(other.substring(1))
    } else {
        if (f.isItem() && s.isItem()) {
            f.compareTo(s)
        } else if (f == '[' && s.isItem()) {
            this.compareNesting("[$s]${other.substring(1)}")
        } else if (s == '[' && f.isItem()) {
            "[$f]${this.substring(1)}".compareNesting(other)
        } else if (f == ']' || s == ',') {
            -1
        } else if (s == ']' || f == ',') {
            1
        } else {
            throw IllegalArgumentException("Unexpected token: $f | $s")
        }
    }
}

fun Char.isItem() = isDigit() || this == 'T'