/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.gateway.rest.entities

import com.google.gson.annotations.SerializedName

data class CreateAccessTokenResponse(
    @SerializedName("access_token")
    val accessToken: String
)