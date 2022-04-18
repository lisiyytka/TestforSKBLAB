package com.example.testforskb_lab.presentation.view.reposit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testforskb_lab.data.SQLite.SQLiteHelper
import com.example.testforskb_lab.domain.modelForLocalDB.ReposForLocal
import com.example.testforskb_lab.databinding.FragmentRepositBinding
import com.example.testforskb_lab.domain.model.OwnerConstructor
import com.example.testforskb_lab.parserForDate
import com.example.testforskb_lab.picassoHelper
import com.example.testforskb_lab.presentation.presenter.RepositPresenter
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class RepositFragment(
    private val name: String,
    private val owner: OwnerConstructor,
    private val description: String,
    private val numberOfForks: String,
    private val numberOfStars: String,
    private val date: String,
    private val account: GoogleSignInAccount

) : MvpAppCompatFragment(), RepositView {

    @InjectPresenter
    lateinit var repositPresenter: RepositPresenter

    private var _binding: FragmentRepositBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepositBinding.inflate(inflater, container, false)
        pullFields()
        binding.deleteRepos.visibility = View.GONE
        val helper = SQLiteHelper(requireContext())
        binding.saveRepos.setOnClickListener {
            var repos = ReposForLocal()
            repos.full_name = name
            repos.created_at = date
            repos.description = description
            repos.forks = numberOfForks
            repos.id_saved_user = account.id.toString()
            repos.imageOwner = owner.avatar_url
            repos.owner = owner.login
            repos.watchers = numberOfStars
            helper.insertSavedRepos(repos)
            binding.deleteRepos.visibility = View.VISIBLE
            binding.saveRepos.visibility = View.GONE
        }

        if (account.id.isNullOrEmpty()) {
            binding.saveRepos.visibility = View.GONE
        }

        val listRepos = helper.getMyRepos(account.id.toString())
        for (i in listRepos) {
            if (i.owner.login == owner.login) {
                binding.deleteRepos.visibility = View.VISIBLE
                binding.saveRepos.visibility = View.GONE
            }
        }

        binding.deleteRepos.setOnClickListener {
            helper.deleteReposFromLocalRepositoryies(account.id.toString(), name)
            binding.deleteRepos.visibility = View.GONE
            binding.saveRepos.visibility = View.VISIBLE
        }
        return binding.root
    }

    override fun pullFields() {
        binding.nameRepoTw.text = name
        binding.creatorName.text = owner.login
        binding.description.text = description
        picassoHelper(owner.avatar_url, binding.creatorImg)
        binding.numberOfForks.text = numberOfForks
        binding.numberOfStars.text = numberOfStars
        binding.dateOfCreator.text = parserForDate(date)
    }
}