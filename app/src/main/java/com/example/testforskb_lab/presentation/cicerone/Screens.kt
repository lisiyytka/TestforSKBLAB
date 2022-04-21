package com.example.testforskb_lab.presentation.cicerone

import com.example.testforskb_lab.presentation.view.login.LoginFragment
import com.example.testforskb_lab.domain.model.OwnerConstructor
import com.example.testforskb_lab.domain.modelForLocalDB.UserForLocal
import com.example.testforskb_lab.presentation.view.profile.ProfileFragment
import com.example.testforskb_lab.presentation.view.reposit.RepositFragment
import com.example.testforskb_lab.presentation.view.repositories.RepositoriesFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import de.hdodenhof.circleimageview.CircleImageView

object Screens {
    fun Login() =
        FragmentScreen { LoginFragment() }

    fun Repositories() = FragmentScreen { RepositoriesFragment() }

    fun Reposit(
        full_name: String,
        owner: OwnerConstructor,
        description: String,
        forks: String,
        watchers: String,
        created_at: String
    ) = FragmentScreen {
        RepositFragment(
            full_name,
            owner,
            description,
            forks,
            watchers,
            created_at
        )
    }

    fun Profile() = FragmentScreen { ProfileFragment() }
}