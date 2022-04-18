package com.example.testforskb_lab.presentation.view.reposit

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface RepositView : MvpView {
    fun pullFields()
}