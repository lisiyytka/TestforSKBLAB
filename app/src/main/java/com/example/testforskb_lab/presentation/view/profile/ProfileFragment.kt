package com.example.testforskb_lab.presentation.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.example.testforskb_lab.DI.Scopes
import com.example.testforskb_lab.R
import com.example.testforskb_lab.databinding.FragmentProfileBinding
import com.example.testforskb_lab.picassoHelper
import com.example.testforskb_lab.presentation.presenter.ProfilePresenter
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import de.hdodenhof.circleimageview.CircleImageView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick

class ProfileFragment(
    private val account: GoogleSignInAccount
) : MvpAppCompatFragment(),
    ProfileView {
    @InjectPresenter
    lateinit var profilePresenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter() =
        Toothpick.openScope(Scopes.APP_SCOPE).getInstance(ProfilePresenter::class.java)

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)

        if (account.id.isNullOrEmpty()) {
            binding.linearCreator.visibility = View.GONE
            binding.clearAll.visibility = View.GONE
        } else {
            binding.profileMail.text = account.email.toString()
            picassoHelper(account.photoUrl.toString(), binding.imgProfile)
        }

        binding.clearAll.setOnClickListener {
            profilePresenter.clearAll(account, requireContext())
        }

        binding.logoutButton.setOnClickListener {
            profilePresenter.logOut(toolbar, requireContext())
        }

        return binding.root
    }
}