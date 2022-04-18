package com.example.testforskb_lab.presentation.presenter

import android.content.Context
import com.example.testforskb_lab.data.SQLite.SQLiteHelper
import com.example.testforskb_lab.domain.modelForLocalDB.UserForLocal
import com.example.testforskb_lab.presentation.cicerone.Screens.Repositories
import com.example.testforskb_lab.presentation.view.login.LoginView
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
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