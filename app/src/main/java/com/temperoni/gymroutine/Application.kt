package com.temperoni.gymroutine.component

import android.app.Application

/**
 * @author Leandro Temperoni
 */
class Application : Application(), RoutinesComponentProvider {

    private val recipesApplicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override val routinesComponent: RoutinesComponent
        get() = recipesApplicationComponent.routinesComponent
}
