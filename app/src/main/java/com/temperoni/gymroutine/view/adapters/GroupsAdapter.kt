package com.temperoni.gymroutine.view.adapters

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.temperoni.gymroutine.R
import com.temperoni.gymroutine.repository.model.Group
import com.temperoni.gymroutine.view.adapters.GroupsAdapter.GroupItemViewHolder
import kotlinx.android.synthetic.main.new_group_item.view.*

/**
 * @author Leandro Temperoni
 */
class GroupsAdapter(private val listener: GroupItemListener) : Adapter<GroupItemViewHolder>() {

    var groups: List<Group> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupItemViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.new_group_item, parent, false)
        return GroupItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    override fun onBindViewHolder(holder: GroupItemViewHolder, position: Int) {
        holder.name.text = "Group ${position + 1}"
        holder.exercises.text = if (groups[position].exercises.isEmpty()) {
            "0 exercises. Tap to add some!"
        } else {
            "${groups[position].exercises.size} exercises. Tap to add some more!"
        }
        holder.cross.setOnClickListener { listener.onCrossClick(position) }
        holder.container.setOnClickListener { listener.onGroupClick(position) }
    }

    inner class GroupItemViewHolder(itemView: View,
                                    val name : TextView = itemView.name,
                                    val exercises : TextView = itemView.exercises,
                                    val cross : ImageView = itemView.cross,
                                    val container : ConstraintLayout = itemView.container) : ViewHolder(itemView)

    interface GroupItemListener {
        fun onCrossClick(position: Int)
        fun onGroupClick(position: Int)
    }
}