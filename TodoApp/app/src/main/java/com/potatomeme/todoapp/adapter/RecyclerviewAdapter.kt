package com.potatomeme.todoapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.potatomeme.todoapp.databinding.ItemRecyclerviewBinding
import com.potatomeme.todoapp.model.Todo

class RecyclerviewAdapter(val listener: EventListener) :
    RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>() {

    interface EventListener {
        fun onClickEventListenr(todo: Todo)
    }

    private val list = ArrayList<Todo>()

    fun addItem(item: Todo) {
        list.add(item)
        Log.d(TAG, "addItem: addeditem ${item} ,total Size : ${list.size} ")
        notifyItemChanged(list.size - 1)
    }

    fun updateItem(todo: Todo) {
        val currentItemIdx = list.indexOfFirst { it.id == todo.id }
        list[currentItemIdx] = todo
        notifyItemChanged(currentItemIdx)
    }

    fun deleteTodo(id:Int) {
        val currentItemIdx = list.indexOfFirst { it.id == id }
        list.removeAt(currentItemIdx)
        notifyItemRemoved(currentItemIdx)
    }

    fun addItems(items: List<Todo>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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
            root.setOnClickListener { listener.onClickEventListenr(todo) }
            titleTextView.text = todo.title
            descriptionTextView.text = todo.description
        }
    }

    companion object {
        private const val TAG = "RecyclerviewAdapter"
    }
}