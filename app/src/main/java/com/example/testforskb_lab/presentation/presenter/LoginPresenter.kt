package com.example.testforskb_lab.presentation.presenter

import android.annotation.SuppressLint
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
import com.example.testforskb_lab.domain.interactors.InteractorsForLocalDB
import com.example.testforskb_lab.domain.modelForLocalDB.UserForLocal
import com.example.testforskb_lab.presentation.cicerone.Screens.Repositories
import com.example.testforskb_lab.presentation.view.login.LoginView
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.util.SharedPreferencesUtils
import de.hdodenhof.circleimageview.CircleImageView
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(private val router: Router, private val interactorsForLocalDB: InteractorsForLocalDB) : MvpPresenter<LoginView>() {
    @SuppressLint("CommitPrefEdits")
    fun signIn(
        result: ActivityResult
    ) {
        val data = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val user = UserForLocal()
            user.id = account.id.toString()
            user.photoUrl = account.photoUrl.toString()
            user.email = account.email.toString()
//            val editor = sharedPreferences.edit()
//            editor.putString("ID",account.id.toString())
//            editor.putString("PHOTOURL",account.photoUrl.toString())
//            editor.putString("EMAIL",account.email.toString())

            interactorsForLocalDB.insertUser(user)
            router.navigateTo(Repositories())
        } catch (e: ApiException) {
            Log.w("asds", "Google sign in failed", e)
        }
    }

    fun signInWithoutGoogle(context: Context) {
        interactorsForLocalDB.deleteUser(interactorsForLocalDB.getUser().id)
        router.navigateTo(Repositories())
    }
}