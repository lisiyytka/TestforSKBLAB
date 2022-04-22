package com.example.testforskb_lab.presentation.view.repositories

import com.example.testforskb_lab.domain.model.RepositoriesConstructor
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface RepositoriesView : MvpView {
    fun searchRepos()
    fun showRepos(listRepositories: List<RepositoriesConstructor>)
}