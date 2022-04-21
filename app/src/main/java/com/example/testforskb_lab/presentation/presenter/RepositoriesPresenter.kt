package com.example.testforskb_lab.presentation.presenter

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.example.testforskb_lab.presentation.cicerone.Screens
import com.example.testforskb_lab.domain.model.Repositories
import com.example.testforskb_lab.presentation.view.repositories.RepositoriesView
import com.example.testforskb_lab.DI.retrofit.RetrofitClient
import com.example.testforskb_lab.data.SQLite.SQLiteHelper
import com.example.testforskb_lab.domain.model.RepositoriesConstructor
import com.example.testforskb_lab.domain.modelForLocalDB.ReposForLocal
import com.example.testforskb_lab.domain.modelForLocalDB.UserForLocal
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class RepositoriesPresenter @Inject constructor(private val router: Router) :
    MvpPresenter<RepositoriesView>() {

    private val emptyList: ArrayList<RepositoriesConstructor> = ArrayList()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    fun setSearchRepositories(query: String) {
        compositeDisposable.add(
            RetrofitClient.buildService()
                .getSearchRepos(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response -> onResponse(response) }, { t -> onFailure(t) })
        )
    }

    private fun onResponse(list: Repositories) {
        viewState.showRepos(ArrayList(list.items))
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
        return helper.getMyRepos(account.id)
    }

    fun onListItemClick(position: Int, list: ArrayList<RepositoriesConstructor>) {
        val repository = ReposForLocal()
        repository.full_name = list[position].full_name
        repository.owner= list[position].owner.login
        repository.description = list[position].description
        repository.forks = list[position].forks
        repository.watchers = list[position].watchers
        repository.created_at = list[position].created_at

        var bundle = Bundle()
        bundle.putSerializable("REPOSITORY", repository)

        router.navigateTo(
            Screens.Reposit(
                list[position].full_name,
                list[position].owner,
                list[position].description,
                list[position].forks,
                list[position].watchers,
                list[position].created_at
            )
        )
    }
}