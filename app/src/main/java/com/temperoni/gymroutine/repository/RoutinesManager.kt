package com.temperoni.gymroutine.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.temperoni.gymroutine.repository.dto.RoutineDto
import com.temperoni.gymroutine.repository.event.RoutinesEvent
import com.temperoni.gymroutine.repository.event.UpdateRoutineEvent
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class RoutinesManager @Inject constructor(val bus: EventBus) {

    fun getRoutines() {
        // TODO replace the documentId for the logged user id
        FirebaseFirestore.getInstance().collection("data")
                .document("XlIvZKNMOJ4TTyRHiiLz")
                .collection("routines")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result = mutableListOf<RoutineDto>()
                        task.result.forEach {
                            with(it) {
                                val item = toObject(RoutineDto::class.java)
                                item.id = id
                                result.add(item)
                            }
                        }
                        bus.post(RoutinesEvent(result))
                    }
                }
    }

    fun saveRoutine(routine: RoutineDto) {
        FirebaseFirestore.getInstance().collection("data")
                .document("XlIvZKNMOJ4TTyRHiiLz")
                .collection("routines")
                .run {
                    if (routine.id.isEmpty()) {
                        add(routine)
                    } else {
                        document(routine.id)
                                .set(routine)
                    }
                }
                .addOnCompleteListener { task ->
                    bus.post(UpdateRoutineEvent(task))
                }
    }
}