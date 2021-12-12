package com.example.testforskb_lab.main

import com.example.testforskb_lab.cicerone.Screens.Login
import com.github.terrakok.cicerone.Router
import de.hdodenhof.circleimageview.CircleImageView
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainActivityPresenter @Inject constructor(private val router: Router) :
    MvpPresenter<MainActivityView>() {
    fun startApp(toolbar: androidx.appcompat.widget.Toolbar, profileImage: CircleImageView) {
        router.navigateTo(Login(toolbar, profileImage))
    }
}