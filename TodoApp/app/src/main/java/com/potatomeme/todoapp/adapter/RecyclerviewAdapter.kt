package com.potatomeme.todoapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.potatomeme.todoapp.databinding.ItemRecyclerviewBinding
import com.potatomeme.todoapp.model.Todo

class RecyclerviewAdapter(val listener: EventListener) :
    ListAdapter<Todo, RecyclerviewAdapter.ViewHolder>(diffUtil) {

    interface EventListener {
        fun onClickEventListenr(todo: Todo)
        fun changedSwitch(todoId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) = with(binding) {
            root.setOnClickListener { listener.onClickEventListenr(todo) }
            titleTextView.text = todo.title
            descriptionTextView.text = todo.description
            favoriteSwitch.isChecked = todo.favoriteTag
            favoriteSwitch.setOnCheckedChangeListener { _, _ ->
                listener.changedSwitch(todo.id)
            }
        }
    }

    companion object {
        private const val TAG = "RecyclerviewAdapter"
        val diffUtil = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return (oldItem.id == newItem.id)
                        && (oldItem.title == newItem.title)
                        && (oldItem.description == newItem.description)
            }
        }
    }
}