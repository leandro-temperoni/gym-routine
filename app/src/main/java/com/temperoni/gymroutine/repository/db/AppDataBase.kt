package com.temperoni.gymroutine.repository.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.temperoni.gymroutine.repository.db.dao.RoutinesDao
import com.temperoni.gymroutine.repository.db.model.RoutineDb

/**
 * @author Leandro Temperoni
 */
@Database(entities = [RoutineDb::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun routinesDao(): RoutinesDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "gym-routines.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}