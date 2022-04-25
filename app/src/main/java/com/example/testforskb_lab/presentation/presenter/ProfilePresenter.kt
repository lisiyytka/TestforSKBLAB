package com.example.testforskb_lab.presentation.presenter

import android.content.Context
import android.view.View
import com.example.testforskb_lab.data.SQLite.SQLiteHelper
import com.example.testforskb_lab.presentation.cicerone.Screens
import com.example.testforskb_lab.createClient
import com.example.testforskb_lab.domain.interactors.InteractorsForLocalDB
import com.example.testforskb_lab.presentation.view.profile.ProfileView
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import de.hdodenhof.circleimageview.CircleImageView
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor(private val router: Router, private val interactorsForLocalDB: InteractorsForLocalDB) : MvpPresenter<ProfileView>(){
    fun clearAll() {
        val account = interactorsForLocalDB.getUser()
        interactorsForLocalDB.deleteSavedRepos(account.email)
    }
    fun logOut(context: Context) {
        createClient(context).signOut()
        router.navigateTo(Screens.Login())
    }

    fun showProfile(){
        viewState.showProfileInfo(interactorsForLocalDB.getUser())
    }
}