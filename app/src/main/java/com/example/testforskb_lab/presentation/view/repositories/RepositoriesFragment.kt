package com.example.testforskb_lab.presentation.view.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testforskb_lab.DI.Scopes
import com.example.testforskb_lab.data.SQLite.SQLiteHelper
import com.example.testforskb_lab.presentation.view.adapters.RecyclerReposAdapter
import com.example.testforskb_lab.databinding.FragmentRepositoriesBinding
import com.example.testforskb_lab.domain.model.RepositoriesConstructor
import com.example.testforskb_lab.presentation.presenter.RepositoriesPresenter
import com.example.testforskb_lab.startLoading
import com.example.testforskb_lab.stopLoading
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import de.hdodenhof.circleimageview.CircleImageView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick
import toothpick.ktp.extension.getInstance

class RepositoriesFragment(
    private val toolbar: androidx.appcompat.widget.Toolbar,
    private val account: GoogleSignInAccount,
    private val imageProfile: CircleImageView
) : MvpAppCompatFragment(), RepositoriesView {

    @InjectPresenter
    lateinit var repositoriesPresenter: RepositoriesPresenter

    private val emptyList: ArrayList<RepositoriesConstructor> = ArrayList()

    @ProvidePresenter
    fun providePresenter() =
        Toothpick.openScope(Scopes.APP_SCOPE).getInstance(RepositoriesPresenter::class.java)

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
        binding.progressView.visibility = View.GONE

        repositoriesPresenter.emptyList(binding.search.text.toString())

        binding.searchIcon.setOnClickListener {
            startLoading(binding.progressView)
            searchRepos()
        }

        toolbar.visibility = View.VISIBLE

        imageProfile.setOnClickListener {
            repositoriesPresenter.onProfile(account, toolbar, imageProfile)
        }

        binding.preservedButton.setOnClickListener {
            binding.preservedButton.isSelected = true
            binding.repositoriesButton.isSelected = false
            showPreserved()
        }

        if (account.id.isNullOrEmpty()) {
            binding.linearLayout.visibility = View.GONE
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
        val listAllSavedRepos = repositoriesPresenter.getRepos(requireContext(),account)
        binding.recyclerView.adapter = RecyclerReposAdapter(
            listAllSavedRepos,
            Toothpick.openScope(Scopes.APP_SCOPE).getInstance(), account
        )

        binding.searchIcon.setOnClickListener {
            val list: ArrayList<RepositoriesConstructor> = ArrayList()
            for (i in listAllSavedRepos) {
                if (i.full_name.lowercase().contains(binding.search.text.toString().lowercase()) ||
                    i.description.lowercase().contains(binding.search.text.toString().lowercase())) {
                    list.add(i)
                }
            }
            binding.recyclerView.adapter = RecyclerReposAdapter(
                list,
                Toothpick.openScope(Scopes.APP_SCOPE).getInstance(), account
            )
        }
    }

    override fun showRepos(listRepositories: ArrayList<RepositoriesConstructor>) {
        stopLoading(binding.progressView)
        binding.recyclerView.adapter = RecyclerReposAdapter(
            listRepositories,
            Toothpick.openScope(Scopes.APP_SCOPE).getInstance(), account
        )

    }
}