package com.temperoni.gymroutine.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.temperoni.gymroutine.repository.model.Group
import com.temperoni.gymroutine.repository.model.Routine
import javax.inject.Inject

/**
 * @author Leandro Temperoni
 */
class AddEditRoutineViewModel @Inject constructor() : ViewModel() {

    val routine: MutableLiveData<Routine> = MutableLiveData()

    fun addGroup() {
        routine.value?.groups?.add(Group())
    }
}