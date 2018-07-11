package com.temperoni.gymroutine.view.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import com.temperoni.gymroutine.R
import com.temperoni.gymroutine.view.model.Routine
import com.temperoni.gymroutine.view.adapters.RoutinesAdapter
import com.temperoni.gymroutine.viewmodel.RoutinesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject
import android.view.MenuItem
import com.firebase.ui.auth.AuthUI

class MainActivity : BaseActivity(), RoutinesAdapter.RoutineItemClickListener {

    @Inject
    lateinit var model: RoutinesViewModel

    private val routinesAdapter = RoutinesAdapter(this)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.sign_out -> signOut()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRoutineItemClick(routine: Routine) {
        val intent = Intent(this, AddEditRoutineActivity::class.java)
        // TODO this should not be serializable, we should be passing the id only
        // and retrieve the routine from local db
        intent.putExtra("routine", routine)
        startActivityForResult(intent, 124)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 124 && resultCode == RESULT_OK) {
            showSnackBar((data?.getSerializableExtra("data") as Pair<Boolean, String>).second)
        }
    }

    private val routinesObserver: Observer<List<Routine>> = Observer { list ->
        list?.let { it ->
            routinesAdapter.routines = it
        }
        ptr.isRefreshing = false
    }

    private fun showSnackBar(message: String) {
        // TODO customize snack bar so as to have success and error type
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_LONG).show()
    }

    private fun signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                        launchSplashScreen()
                    else showSnackBar(task.exception?.localizedMessage.toString())
                }
    }

    private fun launchSplashScreen() {
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }
}