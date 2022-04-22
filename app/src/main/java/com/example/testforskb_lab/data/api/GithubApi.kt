package com.example.testforskb_lab.data.api

import com.example.testforskb_lab.data.model.RepositoriesResponse
import com.example.testforskb_lab.domain.model.Repositories
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories")
    fun getSearchRepos(
        @Query("q") query: String
    ): Observable<RepositoriesResponse>
}