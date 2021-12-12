package com.example.testforskb_lab.login

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.testforskb_lab.SQLite.SQLiteHelper
import com.example.testforskb_lab.SQLite.modelsForLocal.UserForLocal
import com.example.testforskb_lab.Screens.Repositories
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import de.hdodenhof.circleimageview.CircleImageView
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(private val router: Router) : MvpPresenter<LoginView>() {
    fun signIn(
        account: GoogleSignInAccount,
        toolbar: androidx.appcompat.widget.Toolbar,
        context: Context,
        profileImageView: CircleImageView
    ) {
        val helper = SQLiteHelper(context)
        var user = UserForLocal()
        user.id = account.id.toString()
        helper.insertUser(user)
        router.navigateTo(Repositories(toolbar, account, profileImageView))
    }

    fun signInWithoutGoogle(
        account: GoogleSignInAccount,
        toolbar: androidx.appcompat.widget.Toolbar,
        profileImageView: CircleImageView
    ) {
        router.navigateTo(Repositories(toolbar, account, profileImageView))
    }
}