package com.example.testforskb_lab.presentation.presenter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import com.example.testforskb_lab.presentation.cicerone.Screens
import com.example.testforskb_lab.domain.model.Repositories
import com.example.testforskb_lab.presentation.view.repositories.RepositoriesView
import com.example.testforskb_lab.DI.retrofit.RetrofitClient
import com.example.testforskb_lab.data.SQLite.SQLiteHelper
import com.example.testforskb_lab.domain.interactors.InteractorForResponse
import com.example.testforskb_lab.domain.model.RepositoriesConstructor
import com.example.testforskb_lab.domain.modelForLocalDB.ReposForLocal
import com.example.testforskb_lab.domain.modelForLocalDB.UserForLocal
import com.example.testforskb_lab.presentation.view.reposit.REPOSITORY
import com.example.testforskb_lab.presentation.view.reposit.RepositFragment
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class RepositoriesPresenter @Inject constructor(
    private val router: Router,
    private val interactor: InteractorForResponse
) :
    MvpPresenter<RepositoriesView>() {

    private val emptyList: ArrayList<RepositoriesConstructor> = ArrayList()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun setSearchRepositories(query: String) {
        compositeDisposable.add(
            interactor.searchRepositories(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response -> onResponse(response) }, { t -> onFailure(t) })
        )
    }

    private fun onResponse(list: List<RepositoriesConstructor>) {
        viewState.showRepos(list)
    }

    private fun onFailure(t: Throwable) {
        Log.d("sda", t.message!!)
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
        context: Context,
        account: UserForLocal
    ): ArrayList<RepositoriesConstructor> {
        val helper = SQLiteHelper(context)
        return helper.getMyRepos(account.email)
    }

    fun onListItemClick(position: Int, list: List<RepositoriesConstructor>) {
        val repository = ReposForLocal()
        repository.full_name = list[position].full_name
        repository.owner = list[position].owner.login
        repository.imageOwner = list[position].owner.avatar_url
        repository.description = list[position].description
        repository.forks = list[position].forks
        repository.watchers = list[position].watchers
        repository.created_at = list[position].created_at
        val jsonRepo = Json.encodeToString(ReposForLocal.serializer(), repository)
        val fragment = RepositFragment()
        fragment.arguments = Bundle().apply { putString(REPOSITORY, jsonRepo) }
        router.navigateTo(
            Screens.Reposit(fragment)
        )
        Log.d("asd", repository.imageOwner)
    }
}