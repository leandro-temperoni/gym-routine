package com.temperoni.gymroutine.repository.dto

/**
 * @author Leandro Temperoni
 */
data class RoutineDto(
        val id: String = "",
        val name: String = "",
        val groups: List<GroupDto?>? = null)