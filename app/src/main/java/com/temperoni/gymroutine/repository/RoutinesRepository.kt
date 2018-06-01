package com.temperoni.gymroutine.repository

import javax.inject.Inject

/**
 * @author Leandro Temperoni
 */
class RoutinesRepository @Inject constructor(val manager: RoutinesManager) {

    fun getRoutines() {
        // TODO add Room support so as to complete the pattern
        manager.getRoutines()
    }
}