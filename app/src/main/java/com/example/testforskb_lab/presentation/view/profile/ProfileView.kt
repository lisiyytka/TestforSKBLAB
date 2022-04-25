package com.example.testforskb_lab.presentation.view.profile

import com.example.testforskb_lab.domain.modelForLocalDB.UserForLocal
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface ProfileView: MvpView {
    fun showProfileInfo(account : UserForLocal)
}