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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.temperoni.gymroutine.R
import com.temperoni.gymroutine.repository.model.Group
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit_routine_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(AddEditRoutineViewModel::class.java)

        model.getGroups().observe(this, observer)

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

    private val observer: Observer<MutableList<Group>> = Observer {
        it?.let {
            groupsAdapter.groups = it
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
