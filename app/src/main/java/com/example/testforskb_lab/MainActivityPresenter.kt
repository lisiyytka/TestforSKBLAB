package com.example.testforskb_lab

import android.view.View
import android.widget.Toolbar
import com.example.testforskb_lab.Screens.Login
import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainActivityPresenter @Inject constructor(private val router: Router) : MvpPresenter<MainActivityView>() {
    fun startApp(toolbar: androidx.appcompat.widget.Toolbar) {
        router.navigateTo(Login(toolbar))
    }

    fun logOut(toolbar: androidx.appcompat.widget.Toolbar) {
        router.navigateTo(Login(toolbar))
        toolbar.visibility = View.GONE
    }
}