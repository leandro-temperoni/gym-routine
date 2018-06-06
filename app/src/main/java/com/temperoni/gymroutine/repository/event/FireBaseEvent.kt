package com.temperoni.gymroutine.repository.event

import com.google.android.gms.tasks.Task

/**
 * @author Leandro Temperoni
 */
open class FireBaseEvent(val task: Task<out Any>) {

    val isSuccesfull = task.isSuccessful
    val payload = task.result
    val error = task.exception
}