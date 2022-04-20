package com.example.testforskb_lab.domain.model

data class Repositories(
    val items: ArrayList<RepositoriesConstructor>
) {
    operator fun get(position: Int): RepositoriesConstructor {
        return items[position]
    }
}