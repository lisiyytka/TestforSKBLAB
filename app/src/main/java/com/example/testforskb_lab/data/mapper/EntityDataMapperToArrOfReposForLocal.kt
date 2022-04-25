package com.example.testforskb_lab.data.mapper

import com.example.testforskb_lab.data.model.RepositoriesResponse
import com.example.testforskb_lab.data.model.RepositoryResponse
import com.example.testforskb_lab.domain.interactors.InteractorForResponse
import com.example.testforskb_lab.domain.model.Repositories
import com.example.testforskb_lab.domain.model.RepositoriesConstructor
import com.example.testforskb_lab.domain.modelForLocalDB.ReposForLocal
import javax.inject.Inject

class EntityDataMapperToArrOfReposForLocal @Inject constructor() {
    
    fun map(repositories: RepositoriesResponse): List<RepositoriesConstructor>{
        return repositories.items.map { 
            transform(it)
        }
    }

    private fun transform(repositories: RepositoryResponse): RepositoriesConstructor {
        val reposForLocal = RepositoriesConstructor()
        reposForLocal.full_name = repositories.full_name
        reposForLocal.description = repositories.description.orEmpty()
        reposForLocal.created_at = repositories.created_at
        reposForLocal.watchers = repositories.watchers
        reposForLocal.forks = repositories.forks
        reposForLocal.owner = repositories.owner
        return reposForLocal
    }

     fun transformResponseToLocal(position: Int, list: List<RepositoriesConstructor>): ReposForLocal {
        val repository = ReposForLocal()
        repository.full_name = list[position].full_name
        repository.owner = list[position].owner.login
        repository.imageOwner = list[position].owner.avatar_url
        repository.description = list[position].description
        repository.forks = list[position].forks
        repository.watchers = list[position].watchers
        repository.created_at = list[position].created_at
        return repository
    }

}