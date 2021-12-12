package com.example.testforskb_lab.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testforskb_lab.R
import com.example.testforskb_lab.Screens
import com.example.testforskb_lab.models.RepositoriesConstructor
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import javax.inject.Inject

class RecyclerReposAdapter @Inject constructor(
    private val listRepositories: ArrayList<RepositoriesConstructor>,
    private val router: Router,
    private val account: GoogleSignInAccount
) :
    RecyclerView.Adapter<RecyclerReposAdapter.RecyclerReposHolder>() {

    class RecyclerReposHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewLarge = view.findViewById<TextView>(R.id.textViewLarge)
        val textViewSmall = view.findViewById<TextView>(R.id.textViewSmall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerReposHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_for_repositories, parent, false)
        return RecyclerReposHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerReposHolder, position: Int) {
        var repository = listRepositories[position]
        var description = ""
        holder.textViewLarge.text = repository.full_name
        holder.textViewSmall.text = repository.description
        if (!repository.description.isNullOrEmpty())
            description = repository.description
        holder.itemView.setOnClickListener {
            router.navigateTo(
                Screens.Reposit(
                    repository.full_name,
                    repository.owner,
                    description,
                    repository.forks,
                    repository.watchers,
                    repository.created_at,
                    account
                )
            )
        }
    }

    override fun getItemCount(): Int = listRepositories.size
}