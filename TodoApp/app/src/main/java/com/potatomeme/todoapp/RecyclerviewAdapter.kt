package com.potatomeme.todoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerviewAdapter(private val itemArr: Array<String>) :
    RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(str: String) {
            itemView.findViewById<TextView>(R.id.item_textview).text = str
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.item_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemArr.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text: String = itemArr[position]
        holder.bind(text)
    }
}