package com.example.testforskb_lab

import android.widget.Toolbar
import com.example.testforskb_lab.login.LoginFragment
import com.example.testforskb_lab.models.OwnerConstructor
import com.example.testforskb_lab.reposit.RepositFragment
import com.example.testforskb_lab.repositories.RepositoriesFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import java.security.acl.Owner

object Screens {
    fun Login(toolbar: androidx.appcompat.widget.Toolbar) =
        FragmentScreen { LoginFragment(toolbar) }

    fun Repositories(toolbar: androidx.appcompat.widget.Toolbar, account: GoogleSignInAccount) =
        FragmentScreen { RepositoriesFragment(toolbar, account) }

    fun Reposit(
        full_name: String,
        owner: OwnerConstructor,
        description: String,
        forks: String,
        watchers: String,
        created_at: String,
        account: GoogleSignInAccount
    ) = FragmentScreen {
        RepositFragment(
            full_name,
            owner,
            description,
            forks,
            watchers,
            created_at,
            account
        )
    }
}