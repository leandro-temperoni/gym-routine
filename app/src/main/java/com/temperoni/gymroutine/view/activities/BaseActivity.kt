package com.temperoni.gymroutine.view.activities

import android.support.v7.app.AppCompatActivity
import com.temperoni.gymroutine.component.RoutinesComponentProvider

/**
 * @author Leandro Temperoni
 */
abstract class BaseActivity: AppCompatActivity() {

    open val component by lazy { (applicationContext as RoutinesComponentProvider).routinesComponent }
}