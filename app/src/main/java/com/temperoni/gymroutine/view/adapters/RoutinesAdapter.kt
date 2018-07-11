package com.temperoni.gymroutine.view.adapters

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.temperoni.gymroutine.R
import com.temperoni.gymroutine.view.model.Routine
import kotlinx.android.synthetic.main.routine_item.view.*

/**
 * @author Leandro Temperoni on 02/06/2018.
 */
class RoutinesAdapter(private val listener: RoutineItemClickListener) :
        RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder>() {

    var routines: List<Routine> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.routine_item, parent, false)
        return RoutineViewHolder(view)
    }

    override fun getItemCount() = routines.size

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        holder.name.text = routines[position].name
        holder.container.setOnClickListener {
            listener.onRoutineItemClick(routines[position])
        }
    }

    inner class RoutineViewHolder(
            itemView: View,
            val name: TextView = itemView.name,
            val container: ConstraintLayout = itemView.container) : RecyclerView.ViewHolder(itemView)

    interface RoutineItemClickListener {
        fun onRoutineItemClick(routine: Routine)
    }
}