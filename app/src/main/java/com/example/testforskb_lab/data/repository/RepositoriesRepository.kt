package com.example.testforskb_lab.data.repository

import android.util.Log
import com.example.testforskb_lab.data.api.GithubApi
import com.example.testforskb_lab.data.mapper.EntityDataMapperToArrOfReposForLocal
import com.example.testforskb_lab.domain.model.Repositories
import com.example.testforskb_lab.domain.model.RepositoriesConstructor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepositoriesRepository @Inject constructor(
    private val mapper: EntityDataMapperToArrOfReposForLocal,
    private val api: GithubApi
    ) {

    fun setSearchRepositories(query: String): Observable<List<RepositoriesConstructor>> {
           return api.getSearchRepos(query)
               .map {
                   mapper.map(it)
               }
    }
}