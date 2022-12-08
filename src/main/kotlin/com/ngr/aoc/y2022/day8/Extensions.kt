package com.ngr.aoc.y2022.day8

import java.awt.Point

fun List<List<*>>.width() = this[0].size
fun List<List<*>>.height() = this.size

fun List<List<*>>.widthRange() = (0 until this.width())
fun List<List<*>>.heightRange() = (0 until this.height())

fun Point.move(dir: Dir) =
    apply {
        x += dir.dx
        y += dir.dy
    }