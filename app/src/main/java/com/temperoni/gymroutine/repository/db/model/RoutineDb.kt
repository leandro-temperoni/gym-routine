package com.temperoni.gymroutine.repository.db.model

import android.arch.persistence.room.Entity

/**
 * @author Leandro Temperoni on 18/08/2018.
 */
@Entity
class RoutineDb(val id: String, val name: String, val groupsCount: Int) : BaseObjectDb()