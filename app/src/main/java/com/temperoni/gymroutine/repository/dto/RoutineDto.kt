package com.temperoni.gymroutine.repository.dto

/**
 * @author Leandro Temperoni
 */
data class RoutineDto(val name: String? = null,
                      val groups: List<GroupDto?>? = null)