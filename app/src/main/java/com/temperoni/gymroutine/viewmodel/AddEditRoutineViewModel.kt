package com.temperoni.gymroutine.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.temperoni.gymroutine.repository.RoutinesRepository
import com.temperoni.gymroutine.repository.dto.ExerciseDto
import com.temperoni.gymroutine.repository.dto.GroupDto
import com.temperoni.gymroutine.repository.dto.RoutineDto
import com.temperoni.gymroutine.repository.event.SingleEvent
import com.temperoni.gymroutine.repository.event.UpdateRoutineEvent
import com.temperoni.gymroutine.repository.model.Exercise
import com.temperoni.gymroutine.repository.model.Group
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/**
 * @author Leandro Temperoni
 */
class AddEditRoutineViewModel
@Inject constructor(private val repository: RoutinesRepository, private val bus: EventBus) : ViewModel() {

    private var groups: MutableLiveData<MutableList<Group>>? = null
    val responseStatus = MutableLiveData<SingleEvent<Pair<Boolean, String>>>()

    init {
        bus.register(this)
    }

    fun getGroups(): MutableLiveData<MutableList<Group>> {
        if (groups == null) {
            groups = MutableLiveData()
            groups?.value = mutableListOf(Group())
        }
        return groups as MutableLiveData<MutableList<Group>>
    }

    fun addGroup() {
        // TODO look up for a way to improve this
        val list = groups?.value
        list?.add(Group())
        groups?.value = list
    }

    fun deleteGroup(position: Int) {
        val list = groups?.value
        list?.removeAt(position)
        groups?.value = list
    }

    fun updateExercises(position: Int, exercises: MutableList<Exercise>) {
        val list = groups?.value
        list?.get(position)?.exercises = exercises
        groups?.value = list
    }

    fun addRoutine(name: String) {
        repository.saveRoutine(mapRoutine(name, groups?.value))
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

    private fun mapRoutine(name: String, groups: MutableList<Group>?): RoutineDto {
        val list = mutableListOf<GroupDto>()
        if (groups == null) {
            // post some error here
        } else {
            groups.forEach {
                list.add(GroupDto(mapExercises(it.exercises)))
            }
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