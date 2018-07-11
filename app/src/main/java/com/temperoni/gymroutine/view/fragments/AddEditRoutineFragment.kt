package com.temperoni.gymroutine.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.*
import com.temperoni.gymroutine.R
import com.temperoni.gymroutine.view.model.Routine
import com.temperoni.gymroutine.view.adapters.GroupsAdapter
import com.temperoni.gymroutine.viewmodel.AddEditRoutineViewModel
import kotlinx.android.synthetic.main.fragment_add_edit_routine_fragment.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddEditRoutineFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddEditRoutineFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AddEditRoutineFragment : Fragment(), GroupsAdapter.GroupItemListener {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var model: AddEditRoutineViewModel

    private val groupsAdapter = GroupsAdapter(this)

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment AddEditRoutineFragment.
         */
        @JvmStatic
        fun newInstance() = AddEditRoutineFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit_routine_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(AddEditRoutineViewModel::class.java)

        model.getRoutine().observe(this, observer)

        with(groups) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context, VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this.context, VERTICAL))
            itemAnimator = DefaultItemAnimator()
            adapter = groupsAdapter
        }

        add.setOnClickListener {
            model.addGroup()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.add_edit_routine_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.confirm -> saveRoutine()
            R.id.delete -> deleteRoutine()
        }
        return super.onOptionsItemSelected(item)
    }

    private val observer: Observer<Routine> = Observer {
        it?.let {
            groupsAdapter.groups = it.groups
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun saveRoutine() {
        // TODO add loading here
        model.addRoutine(name.text.toString())
    }

    private fun deleteRoutine() {
        // TODO add loading here
        model.deleteRoutine()
    }

    override fun onCrossClick(position: Int) {
        model.deleteGroup(position)
    }

    override fun onGroupClick(position: Int) {
        listener?.onGroupClick(position)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onGroupClick(position: Int)
    }
}
