package com.temperoni.gymroutine.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.temperoni.gymroutine.repository.RoutinesRepository
import com.temperoni.gymroutine.repository.dto.GroupDto
import com.temperoni.gymroutine.repository.dto.RoutineDto
import com.temperoni.gymroutine.repository.event.RoutinesEvent
import com.temperoni.gymroutine.repository.model.Exercise
import com.temperoni.gymroutine.repository.model.Group
import com.temperoni.gymroutine.repository.model.Routine
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/**
 * @author Leandro Temperoni
 */
class RoutinesViewModel @Inject constructor(private val repository: RoutinesRepository, private val bus:EventBus) : ViewModel() {

    private var routines: MutableLiveData<List<Routine>>? = null

    init {
        bus.register(this)
    }

    fun getRoutines(): MutableLiveData<List<Routine>> {
        if (routines == null) {
            routines = MutableLiveData()
            repository.getRoutines()
        }
        return routines as MutableLiveData<List<Routine>>
    }

    fun refreshRoutines() {
        repository.getRoutines()
    }

    @Subscribe
    fun onRoutinesEvent(event: RoutinesEvent) {
        routines?.value = mapRoutines(event.data)
    }

    private fun mapRoutines(list: List<RoutineDto>): List<Routine> {
        val data = mutableListOf<Routine>()
        list.forEach {
            val routine = Routine(it.id, it.name)
            it.groups?.forEach {
                it?.let {
                    routine.groups.add(mapGroup(it))
                }
            }
            data.add(routine)
        }
        return data
    }

    private fun mapGroup(group: GroupDto): Group {
        val data = Group()
        val exercises = mutableListOf<Exercise>()
        group.exercises?.forEach {
            exercises.add(Exercise(it?.name ?: "", it?.reps ?: ""))
        }
        data.exercises = exercises
        return data
    }

    override fun onCleared() {
        super.onCleared()
        bus.unregister(this)
    }
}