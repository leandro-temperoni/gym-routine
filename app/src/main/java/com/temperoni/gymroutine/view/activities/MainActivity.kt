package com.temperoni.gymroutine.view.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import com.temperoni.gymroutine.R
import com.temperoni.gymroutine.repository.model.Routine
import com.temperoni.gymroutine.viewmodel.RoutinesViewModel
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var model: RoutinesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component.inject(this)

        model = ViewModelProviders.of(this, factory).get(RoutinesViewModel::class.java)

        model.getRoutines().observe(this, routinesObserver)
    }

    private val routinesObserver: Observer<List<Routine>> = Observer {
        it?.forEach {
            Log.i("pepe", it.name)
        }
    }
}
