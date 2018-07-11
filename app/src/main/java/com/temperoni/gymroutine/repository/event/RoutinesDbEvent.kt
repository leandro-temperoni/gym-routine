package com.temperoni.gymroutine.repository.event

import com.temperoni.gymroutine.repository.db.model.RoutineDb

/**
 * @author Leandro Temperoni on 18/08/2018.
 */
class RoutinesDbEvent(val data: MutableList<RoutineDb>) : SingleEvent<MutableList<RoutineDb>>(data)