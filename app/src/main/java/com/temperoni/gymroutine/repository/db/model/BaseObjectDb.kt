package com.temperoni.gymroutine.repository.db.model

import android.arch.persistence.room.PrimaryKey

/**
 * @author Leandro Temperoni
 */
open class BaseObjectDb {
    @PrimaryKey(autoGenerate = true) var idDb: Int = 0
}