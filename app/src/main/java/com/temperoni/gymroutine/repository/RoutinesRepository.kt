package com.temperoni.gymroutine.repository

import com.temperoni.gymroutine.repository.dto.RoutineDto
import javax.inject.Inject

/**
 * @author Leandro Temperoni
 */
// TODO add Room support so as to complete the pattern
class RoutinesRepository @Inject constructor(private val manager: RoutinesManager) {

    fun getRoutines() {
        manager.getRoutines()
    }

    fun saveRoutine(routine: RoutineDto) {
        manager.saveRoutine(routine)
    }

    fun deleteRoutine(id: String?) {
        id?.let { manager.deleteRoutine(id) }
    }
}