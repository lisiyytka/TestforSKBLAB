package com.example.testforskb_lab.domain.modelForLocalDB

class ReposForLocal {
    var full_name: String = ""
    var owner: String = ""
    var imageOwner: String = ""
    var description: String = ""
    var forks: String = ""
    var watchers: String = ""
    var created_at: String = ""
    var id_saved_user: String = ""

    constructor()

    constructor(
        full_name: String, owner: String, imageOwner: String, description: String,
        forks: String, watchers: String, created_at: String, id_saved_user: String
    ) {
        this.full_name = full_name
        this.owner = owner
        this.description = description
        this.forks = forks
        this.watchers = watchers
        this.created_at = created_at
        this.id_saved_user = id_saved_user
    }

}
