package com.temperoni.gymroutine.component

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import java.lang.annotation.Documented
import kotlin.reflect.KClass

/**
 * @author Leandro Temperoni
 */
@Suppress("DEPRECATED_JAVA_ANNOTATION")
@Documented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)