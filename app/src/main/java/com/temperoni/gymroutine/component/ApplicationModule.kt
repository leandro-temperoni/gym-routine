package com.temperoni.gymroutine.component

import android.app.Application
import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import javax.inject.Singleton

/**
 * @author Leandro Temperoni
 */
@Module
class ApplicationModule(var mApplication: Application) {

    @Provides
    @Singleton
    fun providesContext() = mApplication

    @Provides
    fun providesEventBus(): EventBus = EventBus.getDefault()
}