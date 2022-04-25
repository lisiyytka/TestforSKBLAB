package com.example.testforskb_lab.presentation.view.reposit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testforskb_lab.data.SQLite.SQLiteHelper
import com.example.testforskb_lab.domain.modelForLocalDB.ReposForLocal
import com.example.testforskb_lab.databinding.FragmentRepositBinding
import com.example.testforskb_lab.domain.model.OwnerConstructor
import com.example.testforskb_lab.domain.modelForLocalDB.UserForLocal
import com.example.testforskb_lab.parserForDate
import com.example.testforskb_lab.picassoHelper
import com.example.testforskb_lab.presentation.presenter.RepositPresenter
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.serialization.json.Json
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

const val REPOSITORY = "Repository"

class RepositFragment(
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

        binding.deleteRepos.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(REPOSITORY) }?.apply {
            val repository = Json.decodeFromString(ReposForLocal.serializer(),getString(REPOSITORY)!!)
            pullFields(repository)
            val helper = SQLiteHelper(requireContext())

            binding.saveRepos.setOnClickListener {
                repository.id_saved_user = helper.getUser().email
                helper.insertSavedRepos(repository)
                binding.deleteRepos.visibility = View.VISIBLE
                binding.saveRepos.visibility = View.GONE
            }

            if (helper.getUser().id.isEmpty()) {
                binding.saveRepos.visibility = View.GONE
            }

            val listRepos = helper.getMyRepos(helper.getUser().email)

            for (i in listRepos) {
                if (i.full_name == repository.full_name) {
                    binding.deleteRepos.visibility = View.VISIBLE
                    binding.saveRepos.visibility = View.GONE
                }
            }

            binding.deleteRepos.setOnClickListener {
                helper.deleteReposFromLocalRepositoryies(helper.getUser().email, repository.full_name)
                binding.deleteRepos.visibility = View.GONE
                binding.saveRepos.visibility = View.VISIBLE
            }
        }
    }

    override fun pullFields(repository: ReposForLocal) {
        binding.nameRepoTw.text = repository.full_name
        binding.creatorName.text = repository.owner
        binding.description.text = repository.description
        picassoHelper(repository.imageOwner, binding.creatorImg)
        binding.numberOfForks.text = repository.forks
        binding.numberOfStars.text = repository.watchers
        binding.dateOfCreator.text = parserForDate(repository.created_at)
    }

    companion object{
        fun instance(repository: ReposForLocal): RepositFragment {
            val jsonRepo = Json.encodeToString(ReposForLocal.serializer(), repository)
            val fragment = RepositFragment()
            fragment.arguments = Bundle().apply { putString(REPOSITORY, jsonRepo) }
            return fragment
        }
    }
}