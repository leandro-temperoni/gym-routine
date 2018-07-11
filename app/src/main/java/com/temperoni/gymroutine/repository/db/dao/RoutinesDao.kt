package com.temperoni.gymroutine.repository.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.temperoni.gymroutine.repository.db.model.RoutineDb

/**
 * @author Leandro Temperoni
 */
@Dao
interface RoutinesDao {

    @Query("select * from routinedb")
    fun getRoutines() : MutableList<RoutineDb>

    @Insert(onConflict = REPLACE)
    fun insertAll(vararg routines: RoutineDb)

    @Query("DELETE FROM routinedb")
    fun deleteAll()
}