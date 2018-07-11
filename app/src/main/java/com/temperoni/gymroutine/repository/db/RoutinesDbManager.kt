package com.temperoni.gymroutine.repository.db

import android.content.Context
import com.temperoni.gymroutine.repository.db.model.RoutineDb
import com.temperoni.gymroutine.repository.event.RoutinesDbEvent
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/**
 * @author Leandro Temperoni
 */
class RoutinesDbManager @Inject constructor(private val bus: EventBus,
                                            private val context: Context) {

    private val mDbWorkerThread: DbWorkerThread = DbWorkerThread("RoutinesDbManagerThread")

    init {
        mDbWorkerThread.start()
    }

    fun getRoutines() {
        val task = Runnable {
            val routines = AppDatabase.getInstance(context)?.routinesDao()?.getRoutines()
            routines?.let { bus.post(RoutinesDbEvent(it)) }
        }
        mDbWorkerThread.postTask(task)
    }

    fun saveRoutines(routines: MutableList<RoutineDb>) {
        val task = Runnable {
            AppDatabase.getInstance(context)?.routinesDao()?.deleteAll()
            AppDatabase.getInstance(context)?.routinesDao()?.insertAll(*routines.toTypedArray())
        }
        mDbWorkerThread.postTask(task)
    }

    fun onDestroy() {
        mDbWorkerThread.quit()
        AppDatabase.destroyInstance()
    }
}