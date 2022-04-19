package com.example.testforskb_lab.presentation.presenter

import android.content.Context
import android.util.Log
import com.example.testforskb_lab.presentation.cicerone.Screens
import com.example.testforskb_lab.domain.model.Repositories
import com.example.testforskb_lab.presentation.view.repositories.RepositoriesView
import com.example.testforskb_lab.DI.retrofit.RetrofitClient
import com.example.testforskb_lab.data.SQLite.SQLiteHelper
import com.example.testforskb_lab.domain.model.RepositoriesConstructor
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
class RepositoriesPresenter @Inject constructor(private val router: Router) : MvpPresenter<RepositoriesView>() {

    private val emptyList: ArrayList<RepositoriesConstructor> = ArrayList()

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()
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

    fun onProfile(account: GoogleSignInAccount, toolbar: androidx.appcompat.widget.Toolbar, imageProfile: CircleImageView) {
        router.navigateTo(Screens.Profile(account, toolbar, imageProfile))
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    fun emptyList(textRequest: String){
        if (textRequest == "")
            viewState.showRepos(emptyList)
    }

    fun getRepos(context: Context,account: GoogleSignInAccount): ArrayList<RepositoriesConstructor> {
        val helper = SQLiteHelper(context)
        return helper.getMyRepos(account.id!!)
    }

    fun openRepos(repository: RepositoriesConstructor, description: String){
        val account = GoogleSignInAccount.createDefault()
        router.navigateTo(Screens.Reposit(repository.full_name,
        repository.owner,
        description,
        repository.forks,
        repository.watchers,
        repository.created_at,
        account))
    }
}