package com.ngr.aoc.y2022.day19

class ResourceAmounts(initialResources: Map<Resource, Int> = mapOf()) {

    private val resources = initialResources.toMutableMap()

    fun add(resource: Resource, n: Int) {
        resources[resource] = resources.orZero(resource) + n
    }

    fun addAll(others: ResourceAmounts) {
        others.resources.forEach {
            resources[it.key] = this[it.key] + it.value
        }
    }

    fun clear() =
        resources.clear()

    fun isNotEmpty() =
        resources.isNotEmpty()

    fun clone() =
        ResourceAmounts(resources.toMap())

    operator fun get(resource: Resource) =
        resources.orZero(resource)

    override fun toString() =
        resources.toString()

    private fun MutableMap<Resource, Int>.orZero(resource: Resource) =
        getOrDefault(resource, 0)
}