package com.temperoni.gymroutine.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Handler
import android.util.Log
import com.temperoni.gymroutine.repository.RoutinesRepository
import com.temperoni.gymroutine.repository.db.model.RoutineDb
import com.temperoni.gymroutine.repository.dto.GroupDto
import com.temperoni.gymroutine.repository.dto.RoutineDto
import com.temperoni.gymroutine.repository.event.RoutinesDbEvent
import com.temperoni.gymroutine.repository.event.RoutinesEvent
import com.temperoni.gymroutine.view.model.Exercise
import com.temperoni.gymroutine.view.model.Group
import com.temperoni.gymroutine.view.model.Routine
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/**
 * @author Leandro Temperoni
 */
class RoutinesViewModel @Inject constructor(private val repository: RoutinesRepository, private val bus: EventBus) : ViewModel() {

    private var routines: MutableLiveData<List<Routine>>? = null

    private val uiHandler = Handler()

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
        repository.refreshRoutines()
    }

    @Subscribe
    fun onRoutinesEvent(event: RoutinesEvent) {
        event.getContentIfNotHandled()?.let { data ->
            Log.i("pepe", "Data brought from Server({${data.size}})")
            routines?.value = mapRoutines(data)
            saveRoutinesOnDb(data)
        }
    }

    @Subscribe
    fun onRoutinesDbEvent(event: RoutinesDbEvent) {
        event.getContentIfNotHandled()?.let { data ->
            Log.i("pepe", "Data brought from DB({${data.size}})")
            uiHandler.post { routines?.value = mapRoutinesDb(data) }
        }
    }

    private fun saveRoutinesOnDb(list: MutableList<RoutineDto>) {
        val data = mutableListOf<RoutineDb>()
        list.forEach { routineDto ->
            val routineDb = RoutineDb(routineDto.id, routineDto.name, routineDto.groups?.size ?: 0)
            data.add(routineDb)
        }
        repository.saveRoutinesOnDb(data)
    }

    private fun mapRoutinesDb(list: MutableList<RoutineDb>): List<Routine> {
        val data = mutableListOf<Routine>()
        list.forEach { routineDb ->
            val routine = Routine(routineDb.id, routineDb.name, routineDb.groupsCount)
            data.add(routine)
        }
        return data
    }

    private fun mapRoutines(list: List<RoutineDto>): List<Routine> {
        val data = mutableListOf<Routine>()
        list.forEach { routineDto ->
            val routine = Routine(routineDto.id, routineDto.name)
            routineDto.groups?.forEach { groupDto ->
                groupDto?.let {
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
        repository.onDestroy()
    }
}