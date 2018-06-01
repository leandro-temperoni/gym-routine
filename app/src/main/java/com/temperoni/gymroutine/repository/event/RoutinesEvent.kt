package com.temperoni.gymroutine.repository.event

import com.temperoni.gymroutine.repository.dto.RoutineDto

/**
 * @author Leandro Temperoni
 */
class RoutinesEvent(val data: MutableList<RoutineDto>) {
}