package com.temperoni.gymroutine.view.adapters

import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.temperoni.gymroutine.R
import com.temperoni.gymroutine.repository.model.Routine
import kotlinx.android.synthetic.main.routine_item.view.*

/**
 * @author Leandro Temperoni on 02/06/2018.
 */
class RoutinesAdapter : RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder>() {

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
        holder.description.text = routines[position].description
    }

    inner class RoutineViewHolder(
            itemView: View,
            val name: TextView = itemView.name,
            val description: TextView = itemView.description) : RecyclerView.ViewHolder(itemView)
}