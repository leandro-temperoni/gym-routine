package com.temperoni.gymroutine.repository.model

/**
 * @author Leandro Temperoni
 */
data class Routine(val name: String? = "",
                   val groups: MutableList<Group> = mutableListOf())