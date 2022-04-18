package com.example.testforskb_lab.presentation.cicerone

import com.example.testforskb_lab.presentation.view.login.LoginFragment
import com.example.testforskb_lab.domain.model.OwnerConstructor
import com.example.testforskb_lab.presentation.view.profile.ProfileFragment
import com.example.testforskb_lab.presentation.view.reposit.RepositFragment
import com.example.testforskb_lab.presentation.view.repositories.RepositoriesFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import de.hdodenhof.circleimageview.CircleImageView

object Screens {
    fun Login(toolbar: androidx.appcompat.widget.Toolbar, imageProfile: CircleImageView) =
        FragmentScreen { LoginFragment(toolbar, imageProfile) }

    fun Repositories(
        toolbar: androidx.appcompat.widget.Toolbar,
        account: GoogleSignInAccount,
        imageProfile: CircleImageView
    ) =
        FragmentScreen { RepositoriesFragment(toolbar, account, imageProfile) }

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

    fun Profile(
        account: GoogleSignInAccount,
        toolbar: androidx.appcompat.widget.Toolbar,
        imageProfile: CircleImageView
    ) =
        FragmentScreen { ProfileFragment(account, toolbar, imageProfile) }
}