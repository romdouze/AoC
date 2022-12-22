package com.ngr.aoc.y2022.day18

data class LavaCube(
    val pos: Point3D,
    val openSides: MutableMap<Dir, Boolean> = Dir.values().associateWith { true }.toMutableMap(),
    val exteriorSides: MutableMap<Dir, Boolean> = Dir.values().associateWith { false }.toMutableMap(),
)