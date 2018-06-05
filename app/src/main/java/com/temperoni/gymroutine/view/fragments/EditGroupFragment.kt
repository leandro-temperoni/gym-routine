package com.temperoni.gymroutine.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.temperoni.gymroutine.R
import com.temperoni.gymroutine.repository.model.Group
import com.temperoni.gymroutine.viewmodel.AddEditRoutineViewModel

// the fragment initialization parameters
private const val ARG_POSITION = "position"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EditGroupFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EditGroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EditGroupFragment : Fragment() {

    private var position: Int? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var model: AddEditRoutineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(AddEditRoutineViewModel::class.java)

        model.getGroups().observe(this, observer)
    }

    private val observer: Observer<MutableList<Group>> = Observer {
        it?.let {
            //
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
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param position The position of the group in the ViewModel.
         * @return A new instance of fragment EditGroupFragment.
         */
        @JvmStatic
        fun newInstance(position: Int) =
                EditGroupFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_POSITION, position)
                    }
                }
    }
}
