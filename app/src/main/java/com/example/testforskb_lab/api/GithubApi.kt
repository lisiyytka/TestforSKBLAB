package com.example.testforskb_lab.api

import com.example.testforskb_lab.models.Repositories
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface GithubApi {
    @GET("search/repositories")
    fun getSearchRepos(
        @Query("q") query: String
    ): Observable<Repositories>
}