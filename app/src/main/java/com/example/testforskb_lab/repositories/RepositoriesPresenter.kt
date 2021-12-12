package com.example.testforskb_lab.repositories

import android.app.DownloadManager
import android.util.Log
import android.widget.Toast
import com.example.testforskb_lab.Screens
import com.example.testforskb_lab.models.Repositories
import com.example.testforskb_lab.profile.ProfileFragment
import com.example.testforskb_lab.repositories.RepositoriesView
import com.example.testforskb_lab.retrofit.RetrofitClient
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
}