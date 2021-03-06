package com.example.testforskb_lab.presentation.presenter

import com.example.testforskb_lab.presentation.view.main.MainActivityView
import com.example.testforskb_lab.presentation.cicerone.Screens.Login
import com.github.terrakok.cicerone.Router
import de.hdodenhof.circleimageview.CircleImageView
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainActivityPresenter @Inject constructor(private val router: Router) :
    MvpPresenter<MainActivityView>() {
    fun startApp() {
        router.navigateTo(Login())
    }
}