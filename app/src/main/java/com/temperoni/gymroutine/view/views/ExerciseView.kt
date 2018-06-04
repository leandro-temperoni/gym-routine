package com.temperoni.gymroutine.view.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.temperoni.gymroutine.R

/**
 * @author Leandro Temperoni
 */
class ExerciseView(context: Context?) : View(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.new_exercise_item, null)
    }
}