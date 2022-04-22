package com.example.testforskb_lab.domain.interactors

import com.example.testforskb_lab.data.repository.RepositoriesRepository
import com.example.testforskb_lab.domain.model.Repositories
import com.example.testforskb_lab.domain.model.RepositoriesConstructor
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

open class InteractorForResponse @Inject constructor(private val repository: RepositoriesRepository) {
    fun searchRepositories(query: String): Observable<List<RepositoriesConstructor>> {
        return repository.setSearchRepositories(query)
    }
}