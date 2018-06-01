package com.temperoni.gymroutine.component

import dagger.Component
import javax.inject.Singleton

/**
 * @author Leandro Temperoni
 */
@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    val routinesComponent: RoutinesComponent
}