package com.example.testforskb_lab.domain.model


class RepositoriesConstructor {
    var full_name: String= ""
    var owner: OwnerConstructor = OwnerConstructor("", "")
    var description: String = ""
    var forks: String = ""
    var watchers: String = ""
    var created_at: String = ""

    constructor()

    constructor(
        full_name: String,
        owner: OwnerConstructor,
        description: String,
        forks: String,
        watchers: String,
        created_at: String
    ) {
        this.full_name = full_name
        this.owner = owner
        this.description = description
        this.forks = forks
        this.watchers = watchers
        this.created_at = created_at
    }
}
