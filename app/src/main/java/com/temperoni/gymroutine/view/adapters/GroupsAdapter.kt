package com.temperoni.gymroutine.view.adapters

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.temperoni.gymroutine.R
import com.temperoni.gymroutine.repository.model.Group
import com.temperoni.gymroutine.view.adapters.GroupsAdapter.GroupsViewHolder
import kotlinx.android.synthetic.main.add_group_item.view.*
import kotlinx.android.synthetic.main.new_group_item.view.*

/**
 * @author Leandro Temperoni
 */
class GroupsAdapter(private val listener: GroupItemListener) : RecyclerView.Adapter<GroupsViewHolder>() {

    var groups: List<Group> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        val layout = when (viewType) {
            0 -> R.layout.new_group_item
            else -> R.layout.add_group_item
        }
        val view = LayoutInflater.from(parent.context)
                .inflate(layout, parent, false)
        return GroupsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groups.size + 1
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        when(holder) {
            is GroupItemViewHolder -> bindGroupItem(holder, position)
            is AddGroupViewHolder -> bindAddGroup(holder)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            groups.size -> 1
            else -> 0
        }
    }

    private fun bindGroupItem(holder: GroupItemViewHolder, position: Int) {
        holder.name.text = "Group ${position + 1}"
    }

    private fun bindAddGroup(holder: AddGroupViewHolder) {

    }

    inner class GroupItemViewHolder(itemView: View,
                                    val name : TextView = itemView.name) : GroupsViewHolder(itemView)

    inner class AddGroupViewHolder(itemView: View,
                                   val container: CardView = itemView.container) : GroupsViewHolder(itemView) {
        init {
            container.setOnClickListener {
                listener.onAddGroupListener()
            }
        }
    }

    open inner class GroupsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface GroupItemListener {
        fun onAddGroupListener()
    }
}