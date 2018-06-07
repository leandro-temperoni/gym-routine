package com.temperoni.gymroutine.repository.event

import com.google.android.gms.tasks.Task

/**
 * @author Leandro Temperoni
 */
class UpdateRoutineEvent(task: Task<out Any>) : FireBaseEvent(task)
