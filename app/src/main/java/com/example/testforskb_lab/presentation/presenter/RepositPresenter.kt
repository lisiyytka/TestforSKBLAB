package com.example.testforskb_lab.presentation.presenter

import android.view.View
import com.example.testforskb_lab.domain.interactors.InteractorsForLocalDB
import com.example.testforskb_lab.domain.modelForLocalDB.ReposForLocal
import com.example.testforskb_lab.presentation.view.reposit.RepositView
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class RepositPresenter @Inject constructor(private val interactorsForLocalDB: InteractorsForLocalDB) : MvpPresenter<RepositView>() {
    fun onClickSaveRepository(repository: ReposForLocal){
        repository.id_saved_user = interactorsForLocalDB.getUser().email
        interactorsForLocalDB.insertSavedRepos(repository)
        viewState.onClickSaveRepository()
    }

    fun onClickDeleteRepository(repository: ReposForLocal){
        interactorsForLocalDB.deleteReposFromLocalRepositoryies(interactorsForLocalDB.getUser().email, repository.full_name)
        viewState.onClickDeleteRepository()
    }

    fun isUserEmpty(){
        if (interactorsForLocalDB.getUser().id.isEmpty()) {
            viewState.isUserEmpty()
        }
    }

    fun repositoryFullName(repository: ReposForLocal) {
        val listRepos = interactorsForLocalDB.getMyRepos(interactorsForLocalDB.getUser().email)
        for (i in listRepos) {
            if (i.full_name == repository.full_name) {
                viewState.repositoryFullName()
            }
        }
    }
}