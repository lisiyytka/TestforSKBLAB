package com.example.testforskb_lab.presentation.presenter

import android.content.Context
import android.view.View
import com.example.testforskb_lab.data.SQLite.SQLiteHelper
import com.example.testforskb_lab.presentation.cicerone.Screens
import com.example.testforskb_lab.createClient
import com.example.testforskb_lab.presentation.view.profile.ProfileView
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import de.hdodenhof.circleimageview.CircleImageView
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor(private val router: Router) : MvpPresenter<ProfileView>(){
    fun clearAll(account: GoogleSignInAccount, context: Context) {
        val helper = SQLiteHelper(context)
        helper.deleteSavedRepos(account.id.toString())
    }
    fun logOut(toolbar: androidx.appcompat.widget.Toolbar, context: Context, imageView: CircleImageView) {
        createClient(context).signOut()
        toolbar.visibility = View.GONE
        router.navigateTo(Screens.Login(toolbar, imageView))
    }
}