package com.temperoni.gymroutine.repository.event

import com.google.android.gms.tasks.Task

class UpdateRoutineEvent(task: Task<out Any>) : FireBaseEvent(task)
