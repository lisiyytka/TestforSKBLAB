package com.example.testforskb_lab.presentation.view.login

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.testforskb_lab.DI.Scopes
import com.example.testforskb_lab.createClient
import com.example.testforskb_lab.databinding.FragmentLoginBinding
import com.example.testforskb_lab.presentation.presenter.LoginPresenter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import de.hdodenhof.circleimageview.CircleImageView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick

class LoginFragment(
    private val toolbar: androidx.appcompat.widget.Toolbar,
    private val imageProfile: CircleImageView
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
        binding.signInButton.setOnClickListener {
            val intent = createClient(requireContext()).signInIntent
            mainActivityResultLauncher.launch(intent)
        }

        binding.signInWithoutGoogle.setOnClickListener {
            val account: GoogleSignInAccount = GoogleSignInAccount.createDefault()
            loginPresenter.signInWithoutGoogle(account, toolbar, imageProfile)
        }

        return binding.root

    }

    private var mainActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                loginPresenter.signIn(account, toolbar, requireContext(),imageProfile)
            } catch (e: ApiException) {
                Log.w("asds", "Google sign in failed", e)
            }
        }
}