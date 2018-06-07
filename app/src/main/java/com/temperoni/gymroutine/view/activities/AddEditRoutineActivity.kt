package com.temperoni.gymroutine.view.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.temperoni.gymroutine.R
import com.temperoni.gymroutine.repository.event.SingleEvent
import com.temperoni.gymroutine.repository.model.Routine
import com.temperoni.gymroutine.view.fragments.AddEditRoutineFragment
import com.temperoni.gymroutine.view.fragments.EditGroupFragment
import com.temperoni.gymroutine.viewmodel.AddEditRoutineViewModel
import javax.inject.Inject

class AddEditRoutineActivity : BaseActivity(), AddEditRoutineFragment.OnFragmentInteractionListener, EditGroupFragment.OnFragmentInteractionListener {

    @Inject
    lateinit var model: AddEditRoutineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_routine)

        component.inject(this)

        model = ViewModelProviders.of(this, factory).get(AddEditRoutineViewModel::class.java)

        val routine = intent.extras?.get("routine")
        routine?.let {
            model.setRoutineForEdition(routine as Routine)
        }

        addFragment(AddEditRoutineFragment.newInstance())
    }

    private fun addFragment(fragment: Fragment, anim: Boolean = false) {
        supportFragmentManager
                .beginTransaction()
                .apply {
                    if (anim) addCustomAnimation()
                }
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()

        model.responseStatus.observe(this, Observer {
            goBackWithResult(it)
        })
    }

    private fun goToEditGroup(position: Int) {
        addFragment(EditGroupFragment.newInstance(position), true)
    }

    override fun onGroupClick(position: Int) {
        goToEditGroup(position)
    }

    override fun closeEditGroupFragment() {
        supportFragmentManager.popBackStack()
    }

    private fun goBackWithResult(event: SingleEvent<Pair<Boolean, String>>?) {
        event?.getContentIfNotHandled()?.let {
            setResult(RESULT_OK, Intent().putExtra("data", it))
            finish()
        }
    }
}

fun FragmentTransaction.addCustomAnimation(): FragmentTransaction {
    // TODO review this animations!!
    return setCustomAnimations(
            R.anim.right_to_left,
            R.anim.right_to_left,
            R.anim.left_to_right,
            R.anim.left_to_right)
}