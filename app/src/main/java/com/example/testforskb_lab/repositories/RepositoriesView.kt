package com.example.testforskb_lab.repositories

import com.example.testforskb_lab.models.RepositoriesConstructor
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface RepositoriesView : MvpView {
    fun searchRepos()
    fun showRepos(listRepositories: ArrayList<RepositoriesConstructor>)
}