package com.example.testforskb_lab.presentation.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testforskb_lab.DI.Scopes
import com.example.testforskb_lab.R
import com.example.testforskb_lab.data.SQLite.SQLiteHelper
import com.example.testforskb_lab.domain.model.Repositories
import com.example.testforskb_lab.presentation.cicerone.Screens
import com.example.testforskb_lab.domain.model.RepositoriesConstructor
import com.example.testforskb_lab.presentation.presenter.RepositoriesPresenter
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick
import javax.inject.Inject


class RecyclerReposAdapter(private val listRepositories: ArrayList<RepositoriesConstructor>, val onItemClicked: (position: Int, listRepositories: ArrayList<RepositoriesConstructor>)-> Unit) :
    RecyclerView.Adapter<RecyclerReposAdapter.RecyclerReposHolder>(){


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
        holder.itemView.setOnClickListener{
            onItemClicked(position,listRepositories)
        }
    }

    override fun getItemCount(): Int = listRepositories.size
}