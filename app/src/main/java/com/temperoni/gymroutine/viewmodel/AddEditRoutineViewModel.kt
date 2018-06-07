package com.temperoni.gymroutine.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.temperoni.gymroutine.repository.RoutinesRepository
import com.temperoni.gymroutine.repository.dto.ExerciseDto
import com.temperoni.gymroutine.repository.dto.GroupDto
import com.temperoni.gymroutine.repository.dto.RoutineDto
import com.temperoni.gymroutine.repository.event.DeleteRoutineEvent
import com.temperoni.gymroutine.repository.event.SingleEvent
import com.temperoni.gymroutine.repository.event.UpdateRoutineEvent
import com.temperoni.gymroutine.repository.model.Exercise
import com.temperoni.gymroutine.repository.model.Group
import com.temperoni.gymroutine.repository.model.Routine
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/**
 * @author Leandro Temperoni
 */
class AddEditRoutineViewModel
@Inject constructor(private val repository: RoutinesRepository, private val bus: EventBus) : ViewModel() {

    private var routine: MutableLiveData<Routine>? = null

    val responseStatus = MutableLiveData<SingleEvent<Pair<Boolean, String>>>()

    init {
        bus.register(this)
    }

    fun getRoutine(): MutableLiveData<Routine> {
        if (routine == null) {
            routine = MutableLiveData()
            routine?.value = Routine()
        }
        return routine as MutableLiveData<Routine>
    }

    fun setRoutineForEdition(routine: Routine) {
        getRoutine().value = routine
    }

    fun addGroup() {
        // TODO look up for a way to improve this
        val newRoutine = routine?.value
        newRoutine?.groups?.add(Group())
        routine?.value = newRoutine
    }

    fun deleteGroup(position: Int) {
        val newRoutine = routine?.value
        newRoutine?.groups?.removeAt(position)
        routine?.value = newRoutine
    }

    fun updateExercises(position: Int, exercises: MutableList<Exercise>) {
        val newRoutine = routine?.value
        newRoutine?.groups?.get(position)?.exercises = exercises
        routine?.value = newRoutine
    }

    fun addRoutine(name: String) {
        repository.saveRoutine(mapRoutine(name, routine?.value))
    }

    fun deleteRoutine() {
        repository.deleteRoutine(routine?.value?.id)
    }

    @Subscribe
    fun onUpdateRoutineEvent(event: UpdateRoutineEvent) {
        responseStatus.value = SingleEvent(
                Pair(event.isSuccesfull,
                        if (event.isSuccesfull)
                            "Routine was successfully saved!"
                        else
                            "Ups! There was an error: ${event.error}")
        )
    }

    @Subscribe
    fun onDeleteRoutineEvent(event: DeleteRoutineEvent) {
        responseStatus.value = SingleEvent(
                Pair(true, "Routine was successfully deleted!")
        )
    }

    private fun mapRoutine(name:String, routine: Routine?): RoutineDto {
        val list = mutableListOf<GroupDto>()
        routine?.groups?.forEach {
            list.add(GroupDto(mapExercises(it.exercises)))
        }
        return RoutineDto(name, list)
    }

    private fun mapExercises(exercises: List<Exercise>): List<ExerciseDto> {
        val list = mutableListOf<ExerciseDto>()
        exercises.forEach {
            list.add(ExerciseDto(it.name, it.reps))
        }
        return list
    }

    override fun onCleared() {
        super.onCleared()
        bus.unregister(this)
    }
}