package com.potatomeme.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.potatomeme.todoapp.databinding.ItemRecyclerviewBinding
import com.potatomeme.todoapp.model.Todo

class RecyclerviewAdapter() :
    RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>() {

    private val list = ArrayList<Todo>()
    fun addItems(items: List<Todo>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) = with(binding) {
            itemTextview.text = todo.title
        }
    }
}