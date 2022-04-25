package com.example.testforskb_lab.presentation.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.example.testforskb_lab.DI.Scopes
import com.example.testforskb_lab.R
import com.example.testforskb_lab.data.SQLite.SQLiteHelper
import com.example.testforskb_lab.databinding.FragmentProfileBinding
import com.example.testforskb_lab.domain.modelForLocalDB.UserForLocal
import com.example.testforskb_lab.picassoHelper
import com.example.testforskb_lab.presentation.presenter.ProfilePresenter
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import de.hdodenhof.circleimageview.CircleImageView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick

class ProfileFragment(
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
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)

        profilePresenter.showProfile()

        binding.clearAll.setOnClickListener {
            profilePresenter.clearAll()
        }

        binding.logoutButton.setOnClickListener {
            toolbar.visibility = View.GONE
            profilePresenter.logOut(requireContext())
        }

        return binding.root
    }

    override fun showProfileInfo(account : UserForLocal) {
        if (account.id.isEmpty()) {
            binding.linearCreator.visibility = View.GONE
            binding.clearAll.visibility = View.GONE
        } else {
            binding.profileMail.text = account.email
            picassoHelper(account.photoUrl, binding.imgProfile)
        }
    }
}