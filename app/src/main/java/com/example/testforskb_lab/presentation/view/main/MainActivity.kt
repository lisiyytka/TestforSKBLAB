package com.example.testforskb_lab.presentation.view.main

import android.os.Bundle
import com.example.testforskb_lab.DI.Scopes
import com.example.testforskb_lab.R
import com.example.testforskb_lab.presentation.presenter.MainActivityPresenter
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import de.hdodenhof.circleimageview.CircleImageView
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainActivityView {

    @InjectPresenter
    lateinit var mainActivityPresenter: MainActivityPresenter

    @ProvidePresenter
    fun providePresenter() =
        Toothpick.openScope(Scopes.APP_SCOPE).getInstance(MainActivityPresenter::class.java)

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = AppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this@MainActivity, Toothpick.openScope(Scopes.APP_SCOPE))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityPresenter.startApp()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}
