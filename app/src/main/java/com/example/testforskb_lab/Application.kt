package com.example.testforskb_lab

import android.app.Application
import com.example.testforskb_lab.DI.Scopes
import com.example.testforskb_lab.DI.appModule
import com.google.android.gms.common.util.VisibleForTesting
import de.hdodenhof.circleimageview.BuildConfig
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
        initAppScope(Toothpick.openScope(Scopes.APP_SCOPE))
    }

    @VisibleForTesting
    fun initAppScope(appScope: Scope) {
        appScope.installModules(
            appModule(this)
        )
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction().preventMultipleRootScopes())
        }
    }

    
}