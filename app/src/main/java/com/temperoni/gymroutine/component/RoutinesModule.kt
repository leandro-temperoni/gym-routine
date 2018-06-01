package com.temperoni.gymroutine.component

import android.arch.lifecycle.ViewModelProvider
import com.temperoni.gymroutine.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * @author Leandro Temperoni
 */
@Module
abstract class RoutinesModule {

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}