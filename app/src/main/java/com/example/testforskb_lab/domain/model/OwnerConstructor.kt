package com.example.testforskb_lab.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerConstructor(
    val login: String,
    val avatar_url: String
)