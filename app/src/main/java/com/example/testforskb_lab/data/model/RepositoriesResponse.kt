package com.example.testforskb_lab.data.model

import com.example.testforskb_lab.domain.model.OwnerConstructor
import com.example.testforskb_lab.domain.model.RepositoriesConstructor

class RepositoriesResponse(
    val items: ArrayList<RepositoryResponse>
)

class RepositoryResponse(
    var full_name: String,
    var owner: OwnerConstructor = OwnerConstructor("", ""),
    var description: String?,
    var forks: String,
    var watchers: String,
    var created_at: String
)