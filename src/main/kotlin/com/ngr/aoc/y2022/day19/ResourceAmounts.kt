package com.ngr.aoc.y2022.day19

class ResourceAmounts(initialResources: Map<Resource, Int> = mapOf()) {

    private val resources = initialResources.toMutableMap()

    fun add(resource: Resource, n: Int) {
        resources[resource] = resources.orZero(resource) + n
    }

    operator fun plusAssign(other: Pair<Resource, Int>) {
        resources[other.first] = this[other.first] + other.second
    }

    operator fun get(resource: Resource) =
        resources.orZero(resource)

    fun clone() =
        ResourceAmounts(resources.toMap())

    override fun toString() =
        resources.toString()

    private fun MutableMap<Resource, Int>.orZero(resource: Resource) =
        getOrDefault(resource, 0)
}