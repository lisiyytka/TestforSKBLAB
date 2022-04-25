package com.example.testforskb_lab.presentation.view.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testforskb_lab.DI.Scopes
import com.example.testforskb_lab.R
import com.example.testforskb_lab.data.SQLite.SQLiteHelper
import com.example.testforskb_lab.presentation.view.adapters.RecyclerReposAdapter
import com.example.testforskb_lab.databinding.FragmentRepositoriesBinding
import com.example.testforskb_lab.domain.model.RepositoriesConstructor
import com.example.testforskb_lab.domain.modelForLocalDB.ReposForLocal
import com.example.testforskb_lab.presentation.presenter.RepositoriesPresenter
import com.example.testforskb_lab.startLoading
import com.example.testforskb_lab.stopLoading
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick
import java.util.concurrent.TimeUnit

class RepositoriesFragment(
) : MvpAppCompatFragment(), RepositoriesView {

    @InjectPresenter
    lateinit var repositoriesPresenter: RepositoriesPresenter

    @ProvidePresenter
    fun providePresenter() =
        Toothpick.openScope(Scopes.APP_SCOPE).getInstance(RepositoriesPresenter::class.java)

    private val emptyList: ArrayList<RepositoriesConstructor> = ArrayList()

    private var _binding: FragmentRepositoriesBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        val imageProfile: CircleImageView = requireActivity().findViewById(R.id.profile_image)

        repositoriesPresenter.checkLogin()

        binding.repositoriesButton.isSelected = true
        binding.progressView.visibility = View.GONE

        repositoriesPresenter.search(binding.search)
        repositoriesPresenter.emptyList(binding.search.query.toString())

        toolbar.visibility = View.VISIBLE

        imageProfile.setOnClickListener {
            repositoriesPresenter.onProfile()
        }

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
        repositoriesPresenter.setSearchRepositories(binding.search.query.toString())
    }

    private fun showPreserved() {
        val listAllSavedRepos = repositoriesPresenter.getRepos()
        sendIntoAdapter(listAllSavedRepos)
    }

    override fun showRepos(listRepositories: List<RepositoriesConstructor>) {
        sendIntoAdapter(listRepositories)
    }

    private fun sendIntoAdapter(listRepositories: List<RepositoriesConstructor>) {
        val mRepositoryAdapter = RecyclerReposAdapter(listRepositories) { position, _ ->
            repositoriesPresenter.onListItemClick(
                position,
                listRepositories
            )
        }
        binding.recyclerView.adapter = mRepositoryAdapter
    }

    override fun showLoading(){
        startLoading(binding.progressView)
    }

    override fun hideLoading(){
        stopLoading(binding.progressView)
    }

    override fun showTabs(){
        binding.tabs.visibility = View.GONE
    }
}