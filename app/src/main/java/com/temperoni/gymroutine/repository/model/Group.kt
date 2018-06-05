package com.temperoni.gymroutine.repository.model

import com.temperoni.gymroutine.repository.dto.ExerciseDto

/**
 * @author Leandro Temperoni
 */
data class Group(val exercises: List<Exercise> = mutableListOf()) {

}