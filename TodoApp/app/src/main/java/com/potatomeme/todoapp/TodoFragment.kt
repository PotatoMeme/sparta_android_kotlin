package com.potatomeme.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TodoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_todo, container, false)
        view.findViewById<RecyclerView>(R.id.todo_recyclerview).apply {
            adapter = RecyclerviewAdapter(Array(30) { "todo" })
            layoutManager = LinearLayoutManager(context)
        }
        return view
    }
}