package com.temperoni.gymroutine.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.temperoni.gymroutine.repository.model.Group
import javax.inject.Inject

/**
 * @author Leandro Temperoni
 */
class AddEditRoutineViewModel @Inject constructor() : ViewModel() {

    private var groups: MutableLiveData<MutableList<Group>>? = null

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
}