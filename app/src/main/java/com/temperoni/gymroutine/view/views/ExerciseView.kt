package com.temperoni.gymroutine.view.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import com.temperoni.gymroutine.R
import kotlinx.android.synthetic.main.new_exercise_item.view.*

/**
 * @author Leandro Temperoni
 */
class ExerciseView(context: Context?) : ConstraintLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.new_exercise_item, this, true)
    }

    fun getExercise() : Pair<String, String> {
        return Pair(name.text.toString(), reps.text.toString())
    }
}