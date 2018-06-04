package com.temperoni.gymroutine.view.activities

import android.arch.lifecycle.ViewModelProvider
import android.support.v7.app.AppCompatActivity
import com.temperoni.gymroutine.component.RoutinesComponentProvider
import javax.inject.Inject

/**
 * @author Leandro Temperoni
 */
abstract class BaseActivity: AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    open val component by lazy { (applicationContext as RoutinesComponentProvider).routinesComponent }
}