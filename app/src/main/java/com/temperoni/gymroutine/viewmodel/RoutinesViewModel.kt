package com.temperoni.gymroutine.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.temperoni.gymroutine.repository.RoutinesRepository
import com.temperoni.gymroutine.repository.dto.RoutineDto
import com.temperoni.gymroutine.repository.event.RoutinesEvent
import com.temperoni.gymroutine.repository.model.Routine
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/**
 * @author Leandro Temperoni
 */
class RoutinesViewModel @Inject constructor(val repository: RoutinesRepository, private val bus:EventBus) : ViewModel() {

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

    @Subscribe
    fun onRoutinesEvent(event: RoutinesEvent) {
        routines?.value = mapRoutines(event.data)
    }

    private fun mapRoutines(list: List<RoutineDto>): List<Routine> {
        val data = mutableListOf<Routine>()
        list.forEach { data.add(Routine(it.name)) }
        return data
    }

    override fun onCleared() {
        super.onCleared()
        bus.unregister(this)
    }
}