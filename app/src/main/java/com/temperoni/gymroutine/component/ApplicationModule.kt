package com.temperoni.gymroutine.component

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import javax.inject.Singleton

/**
 * @author Leandro Temperoni
 */
@Module
class ApplicationModule(private var mApplication: Application) {

    @Provides
    @Singleton
    fun providesContext(): Context = mApplication

    @Provides
    fun providesEventBus(): EventBus = EventBus.getDefault()
}