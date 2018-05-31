package com.temperoni.gymroutine.component

import android.app.Activity
import dagger.Subcomponent

/**
 * @author Leandro Temperoni
 */
@Subcomponent
interface RoutinesComponent {

    fun inject(activity: Activity)
}