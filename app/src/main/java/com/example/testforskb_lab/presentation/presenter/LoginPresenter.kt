package com.example.testforskb_lab.presentation.presenter

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.testforskb_lab.createClient
import com.example.testforskb_lab.data.SQLite.SQLiteHelper
import com.example.testforskb_lab.domain.modelForLocalDB.UserForLocal
import com.example.testforskb_lab.presentation.cicerone.Screens.Repositories
import com.example.testforskb_lab.presentation.view.login.LoginView
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import de.hdodenhof.circleimageview.CircleImageView
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(private val router: Router) : MvpPresenter<LoginView>() {
    fun signIn(
        result: ActivityResult,
        context: Context
    ) {
        val data = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val helper = SQLiteHelper(context)
            val account = task.getResult(ApiException::class.java)!!
            val user = UserForLocal()
            user.id = account.id.toString()
            user.photoUrl = account.photoUrl.toString()
            user.email = account.email.toString()
            helper.insertUser(user)
            router.navigateTo(Repositories())
        } catch (e: ApiException) {
            Log.w("asds", "Google sign in failed", e)
        }
    }

    fun signInWithoutGoogle(context: Context) {
        val helper = SQLiteHelper(context)
        helper.deleteUser(helper.getUser().id)
        router.navigateTo(Repositories())
    }
}