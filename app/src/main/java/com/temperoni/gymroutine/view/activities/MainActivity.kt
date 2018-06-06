package com.temperoni.gymroutine.view.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.temperoni.gymroutine.R
import com.temperoni.gymroutine.repository.model.Routine
import com.temperoni.gymroutine.view.adapters.RoutinesAdapter
import com.temperoni.gymroutine.viewmodel.RoutinesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var model: RoutinesViewModel

    private val routinesAdapter = RoutinesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component.inject(this)

        model = ViewModelProviders.of(this, factory).get(RoutinesViewModel::class.java)

        model.getRoutines().observe(this, routinesObserver)

        setSupportActionBar(toolbar)

        with(recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            adapter = routinesAdapter
            addItemDecoration(DividerItemDecoration(this.context, RecyclerView.VERTICAL))
        }

        ptr.setOnRefreshListener {
            model.refreshRoutines()
        }

        fab.setOnClickListener {
            startActivityForResult(Intent(this, AddEditRoutineActivity::class.java), 124)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 124 && resultCode == RESULT_OK) {
            showSnackBar(data?.getSerializableExtra("data") as Pair<Boolean, String>)
        }
    }

    private val routinesObserver: Observer<List<Routine>> = Observer {
        it?.let {
            routinesAdapter.routines = it
        }
        ptr.isRefreshing = false
    }

    private fun showSnackBar(pair: Pair<Boolean, String>) {
        // TODO customize snack bar so as to have success and error type
        Snackbar.make(recyclerView, pair.second, Snackbar.LENGTH_LONG).show()
    }
}