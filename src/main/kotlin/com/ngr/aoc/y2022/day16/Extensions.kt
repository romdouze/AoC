package com.ngr.aoc.y2022.day16

fun String.asOpen() = "$this-O"

fun String.isOpen() = endsWith("-O")