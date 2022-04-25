package com.example.testforskb_lab.data.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Provider

class ProviderRetrofitClient @Inject constructor(): Provider<GithubApi> {

    private val BASE_URL = "https://api.github.com/"

    override fun get(): GithubApi {

        return Retrofit.Builder()
           .baseUrl(BASE_URL)
           .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
           .addConverterFactory(GsonConverterFactory.create())
           .build()
           .create(GithubApi::class.java)
    }
}