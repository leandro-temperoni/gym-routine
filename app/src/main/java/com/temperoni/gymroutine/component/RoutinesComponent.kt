package com.temperoni.gymroutine.component

import com.temperoni.gymroutine.view.activities.AddEditRoutineActivity
import com.temperoni.gymroutine.view.activities.MainActivity
import dagger.Subcomponent

/**
 * @author Leandro Temperoni
 */
@Subcomponent(modules = [(RoutinesModule::class)])
interface RoutinesComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: AddEditRoutineActivity)
}