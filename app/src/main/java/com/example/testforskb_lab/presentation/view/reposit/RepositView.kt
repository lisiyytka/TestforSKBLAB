package com.example.testforskb_lab.presentation.view.reposit

import com.example.testforskb_lab.domain.modelForLocalDB.ReposForLocal
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface RepositView : MvpView {
    fun pullFields(repository: ReposForLocal)
    fun onClickSaveRepository()
    fun isUserEmpty()
    fun repositoryFullName()
    fun onClickDeleteRepository()
}