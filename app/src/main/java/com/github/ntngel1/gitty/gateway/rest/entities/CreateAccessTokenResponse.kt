package com.github.ntngel1.gitty.gateway.rest.entities

import com.google.gson.annotations.SerializedName

data class CreateAccessTokenResponse(
    @SerializedName("access_token")
    val accessToken: String
)