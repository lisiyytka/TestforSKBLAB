package com.example.testforskb_lab.presentation.view.login

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface LoginView : MvpView {
}