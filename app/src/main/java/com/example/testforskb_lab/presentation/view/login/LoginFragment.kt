package com.example.testforskb_lab.presentation.view.login

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import com.example.testforskb_lab.DI.Scopes
import com.example.testforskb_lab.R
import com.example.testforskb_lab.createClient
import com.example.testforskb_lab.databinding.FragmentLoginBinding
import com.example.testforskb_lab.presentation.presenter.LoginPresenter
import de.hdodenhof.circleimageview.CircleImageView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick

class LoginFragment(
) :
    MvpAppCompatFragment(), LoginView {

    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter() =
        Toothpick.openScope(Scopes.APP_SCOPE).getInstance(LoginPresenter::class.java)

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.visibility = View.GONE

        binding.signInButton.setOnClickListener {
            val intent = createClient(requireContext()).signInIntent
            mainActivityResultLauncher.launch(intent)
        }

        binding.signInWithoutGoogle.setOnClickListener {
            loginPresenter.signInWithoutGoogle(requireContext())
        }

        return binding.root
    }

    private var mainActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            loginPresenter.signIn(result,requireContext())
        }
}