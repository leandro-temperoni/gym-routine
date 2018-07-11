package com.temperoni.gymroutine.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.temperoni.gymroutine.repository.dto.RoutineDto
import com.temperoni.gymroutine.repository.event.DeleteRoutineEvent
import com.temperoni.gymroutine.repository.event.RoutinesEvent
import com.temperoni.gymroutine.repository.event.UpdateRoutineEvent
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class RoutinesManager @Inject constructor(private val bus: EventBus) {

    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    fun getRoutines() {
        getRoutinesCollection()
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result = mutableListOf<RoutineDto>()
                        task.result.forEach {
                            with(it) {
                                val item = toObject(RoutineDto::class.java)
                                result.add(item)
                            }
                        }
                        bus.post(RoutinesEvent(result))
                    }
                }
    }

    fun saveRoutine(routine: RoutineDto) {
        getRoutinesCollection()
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

    fun deleteRoutine(id: String) {
        getRoutinesCollection()
                .document(id)
                .delete()
                .addOnSuccessListener {
                    bus.post(DeleteRoutineEvent())
                }
                .addOnFailureListener {
                    // TODO handle this error
                }
    }

    private fun getRoutinesCollection(): CollectionReference {
        return FirebaseFirestore.getInstance()
                .collection("data")
                .document(userId ?: "")
                .collection("routines")
    }
}