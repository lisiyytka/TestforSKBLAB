package com.example.testforskb_lab.DI

import android.content.Context
import com.example.testforskb_lab.data.api.GithubApi
import com.example.testforskb_lab.data.api.ProviderRetrofitClient
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import toothpick.ktp.binding.module

fun appModule(context: Context) = module {
    bind(Context::class.java).toInstance(context)
    val cicerone = Cicerone.create()
    bind(Router::class.java).toInstance(cicerone.router)
    bind(NavigatorHolder::class.java).toInstance(cicerone.getNavigatorHolder())

    bind(GithubApi::class.java).toProvider(ProviderRetrofitClient::class.java)
}