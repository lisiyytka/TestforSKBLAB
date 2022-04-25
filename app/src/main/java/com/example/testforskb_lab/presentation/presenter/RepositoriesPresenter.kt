package com.example.testforskb_lab.presentation.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import android.view.View
import android.widget.SearchView
import com.example.testforskb_lab.presentation.cicerone.Screens
import com.example.testforskb_lab.domain.model.Repositories
import com.example.testforskb_lab.presentation.view.repositories.RepositoriesView
import com.example.testforskb_lab.data.SQLite.SQLiteHelper
import com.example.testforskb_lab.data.mapper.EntityDataMapperToArrOfReposForLocal
import com.example.testforskb_lab.domain.interactors.InteractorForResponse
import com.example.testforskb_lab.domain.interactors.InteractorsForLocalDB
import com.example.testforskb_lab.domain.model.RepositoriesConstructor
import com.example.testforskb_lab.domain.modelForLocalDB.ReposForLocal
import com.example.testforskb_lab.domain.modelForLocalDB.UserForLocal
import com.example.testforskb_lab.presentation.view.reposit.REPOSITORY
import com.example.testforskb_lab.presentation.view.reposit.RepositFragment
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class RepositoriesPresenter @Inject constructor(
    private val router: Router,
    private val interactor: InteractorForResponse,
    private val entity: EntityDataMapperToArrOfReposForLocal,
    private val interactorsForLocalDB: InteractorsForLocalDB
) :
    MvpPresenter<RepositoriesView>() {

    private val emptyList: ArrayList<RepositoriesConstructor> = ArrayList()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun setSearchRepositories(query: String) {
        viewState.showLoading()
        compositeDisposable.add(
            interactor.searchRepositories(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response -> onResponse(response) }, { t -> onFailure(t) })
        )
    }

    private fun onResponse(list: List<RepositoriesConstructor>) {
        viewState.showRepos(list)
        viewState.hideLoading()
    }

    private fun onFailure(t: Throwable) {
        Log.d("sda", t.message!!)
        viewState.hideLoading()
    }

    fun onProfile() {
        router.navigateTo(Screens.Profile())
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    fun emptyList(textRequest: String) {
        if (textRequest == "")
            viewState.showRepos(emptyList)
    }

    fun getRepos(
    ): ArrayList<RepositoriesConstructor> {
        return interactorsForLocalDB.getMyRepos(interactorsForLocalDB.getUser().email)
    }

    fun onListItemClick(position: Int, list: List<RepositoriesConstructor>) {
        val repository = entity.transformResponseToLocal(position, list)
        router.navigateTo(
            Screens.Reposit(repository)
        )
    }

    @SuppressLint("CheckResult")
    fun search(searchView: SearchView){
        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(text: String?): Boolean {
                    subscriber.onNext(text!!)
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    subscriber.onNext(query!!)
                    return false
                }
            })
        })
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinct()
            .filter { text -> text.isNotBlank() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                setSearchRepositories(it)
            }
    }

    fun checkLogin(){
        if (interactorsForLocalDB.getUser().id.isEmpty()) {
            viewState.showTabs()
        }
    }
}