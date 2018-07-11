package com.temperoni.gymroutine.repository

import com.temperoni.gymroutine.repository.db.RoutinesDbManager
import com.temperoni.gymroutine.repository.db.model.RoutineDb
import com.temperoni.gymroutine.repository.dto.RoutineDto
import javax.inject.Inject

/**
 * @author Leandro Temperoni
 */
class RoutinesRepository @Inject constructor(private val manager: RoutinesManager,
                                             private val dbManager: RoutinesDbManager) {

    fun getRoutines() {
        dbManager.getRoutines()
        manager.getRoutines()
    }

    fun refreshRoutines() {
        manager.getRoutines()
    }

    fun saveRoutine(routine: RoutineDto) {
        manager.saveRoutine(routine)
    }

    fun deleteRoutine(id: String?) {
        id?.let { manager.deleteRoutine(id) }
    }

    fun onDestroy() {
        dbManager.onDestroy()
    }

    fun saveRoutinesOnDb(routines: MutableList<RoutineDb>) {
        dbManager.saveRoutines(routines)
    }
}