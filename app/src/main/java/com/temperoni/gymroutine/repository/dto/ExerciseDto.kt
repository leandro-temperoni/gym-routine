package com.temperoni.gymroutine.repository.dto

/**
 * @author Leandro Temperoni on 02/06/2018.
 */
data class ExerciseDto(val name: String? = null,
                       val reps: String? = null) : BaseDto()