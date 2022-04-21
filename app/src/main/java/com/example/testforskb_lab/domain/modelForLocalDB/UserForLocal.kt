package com.example.testforskb_lab.domain.modelForLocalDB

class UserForLocal {
    var id: String = ""
    var email: String = ""
    var photoUrl: String = ""

    constructor()

    constructor(id: String, email: String, photoUrl: String) {
        this.id = id
        this.email = email
        this.photoUrl = photoUrl
    }

}