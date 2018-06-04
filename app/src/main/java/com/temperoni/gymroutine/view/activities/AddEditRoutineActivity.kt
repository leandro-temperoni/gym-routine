package com.temperoni.gymroutine.view.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.temperoni.gymroutine.R
import com.temperoni.gymroutine.repository.model.Routine
import com.temperoni.gymroutine.view.adapters.GroupsAdapter
import com.temperoni.gymroutine.viewmodel.AddEditRoutineViewModel
import kotlinx.android.synthetic.main.activity_add_edit_routine.*
import javax.inject.Inject

class AddEditRoutineActivity : BaseActivity(), GroupsAdapter.GroupItemListener {

    @Inject
    lateinit var model: AddEditRoutineViewModel

    private val groupsAdapter = GroupsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_routine)

        component.inject(this)

        model = ViewModelProviders.of(this, factory).get(AddEditRoutineViewModel::class.java)

        model.routine.observe(this, observer)

        with(groups) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            adapter = groupsAdapter
            addItemDecoration(DividerItemDecoration(this.context, RecyclerView.VERTICAL))
        }
    }

    private val observer: Observer<Routine> = Observer {
        it?.groups?.let { groupsAdapter.groups = it }
    }

    override fun onAddGroupListener() {
        model.addGroup()
    }
}
