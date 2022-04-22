package com.example.testforskb_lab.presentation.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testforskb_lab.R
import com.example.testforskb_lab.domain.model.RepositoriesConstructor


class RecyclerReposAdapter(
    private val listRepositories: List<RepositoriesConstructor>,
    val onItemClicked: (position: Int, listRepositories: List<RepositoriesConstructor>) -> Unit
) :
    RecyclerView.Adapter<RecyclerReposAdapter.RecyclerReposHolder>() {


    class RecyclerReposHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewLarge: TextView = view.findViewById(R.id.textViewLarge)
        val textViewSmall: TextView = view.findViewById(R.id.textViewSmall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerReposHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_for_repositories, parent, false)
        return RecyclerReposHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerReposHolder, position: Int) {
        val repository = listRepositories[position]
        holder.textViewLarge.text = repository.full_name
        holder.textViewSmall.text = repository.description
        holder.itemView.setOnClickListener {
            onItemClicked(position, listRepositories)
        }
    }

    override fun getItemCount(): Int = listRepositories.size
}