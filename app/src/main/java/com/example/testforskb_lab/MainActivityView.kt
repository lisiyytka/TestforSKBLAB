package com.example.testforskb_lab

import com.google.android.material.circularreveal.CircularRevealHelper
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface MainActivityView: MvpView {
}