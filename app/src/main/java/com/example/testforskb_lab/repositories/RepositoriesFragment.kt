package com.example.testforskb_lab.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testforskb_lab.DI.Scopes
import com.example.testforskb_lab.SQLite.SQLiteHelper
import com.example.testforskb_lab.SQLite.modelsForLocal.ReposForLocal
import com.example.testforskb_lab.adapters.RecyclerReposAdapter
import com.example.testforskb_lab.databinding.FragmentRepositoriesBinding
import com.example.testforskb_lab.models.RepositoriesConstructor
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import toothpick.Toothpick
import toothpick.ktp.extension.getInstance

class RepositoriesFragment(
    private val toolbar: androidx.appcompat.widget.Toolbar,
    private val account: GoogleSignInAccount
) : MvpAppCompatFragment(), RepositoriesView {

    @InjectPresenter
    lateinit var repositoriesPresenter: RepositoriesPresenter

    private var _binding: FragmentRepositoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.repositoriesButton.isSelected = true

        binding.searchIcon.setOnClickListener {
            searchRepos()
        }

        toolbar.visibility = View.VISIBLE

        val emptyList: ArrayList<RepositoriesConstructor> = ArrayList()
        if (binding.search.text.toString() == "")
            showRepos(emptyList)

        binding.preservedButton.setOnClickListener {
            binding.preservedButton.isSelected = true
            binding.repositoriesButton.isSelected = false
            showPreserved()
        }

        binding.repositoriesButton.setOnClickListener {
            showRepos(emptyList)
            binding.preservedButton.isSelected = false
            binding.repositoriesButton.isSelected = true
            binding.linearSearch.visibility = View.VISIBLE
            searchRepos()
        }

        return binding.root
    }

    override fun searchRepos() {
        repositoriesPresenter.setSearchRepositories(binding.search.text.toString())
    }

    private fun showPreserved() {
        val helper = context?.let { it1 -> SQLiteHelper(it1) }
        val listAllSavedRepos: ArrayList<RepositoriesConstructor> =
            helper!!.getMyRepos(account.id!!)
        binding.recyclerView.adapter = RecyclerReposAdapter(
            listAllSavedRepos,
            Toothpick.openScope(Scopes.APP_SCOPE).getInstance(), account
        )
        binding.linearSearch.visibility = View.GONE
    }

    override fun showRepos(listRepositories: ArrayList<RepositoriesConstructor>) {
        binding.recyclerView.adapter = RecyclerReposAdapter(
            listRepositories,
            Toothpick.openScope(Scopes.APP_SCOPE).getInstance(), account
        )
    }
}