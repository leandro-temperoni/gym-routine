package com.temperoni.gymroutine.component

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.temperoni.gymroutine.viewmodel.AddEditRoutineViewModel
import com.temperoni.gymroutine.viewmodel.RoutinesViewModel
import com.temperoni.gymroutine.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Leandro Temperoni
 */
@Module
abstract class RoutinesModule {

    @Binds
    @IntoMap
    @ViewModelKey(RoutinesViewModel::class)
    internal abstract fun bindRoutinesViewModel(routinesViewModel: RoutinesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddEditRoutineViewModel::class)
    internal abstract fun bindAddEditRoutineViewModel(addEditRoutineViewModel: AddEditRoutineViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}