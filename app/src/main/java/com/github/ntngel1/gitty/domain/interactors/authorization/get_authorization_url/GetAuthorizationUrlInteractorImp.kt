/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.authorization.get_authorization_url

import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class GetAuthorizationUrlInteractorImp @Inject constructor() :
    GetAuthorizationUrlInteractor {

    override fun invoke(): Single<String> {
        val state = UUID.randomUUID().toString()
        val url = "https://github.com/login/oauth/authorize"
        val scopes = listOf(
            "repo",
            "admin:repo_hook",
            "admin:org",
            "admin:public_key",
            "admin:org_hook",
            "gist",
            "notifications",
            "user",
            "delete_repo",
            "write:discussion",
            "read:packages",
            "delete:packages",
            "admin:gpg_key",
            "workflow"
        ).joinToString(" ")

        val clientId = "69e1406fe0872dcb006f"
        val redirectUrl = "https://localhost:1234/auth"

        val parameters = listOf(
            "client_id=$clientId",
            "redirect_uri=$redirectUrl",
            "scope=$scopes",
            "state=$state",
            "allow_signup=false"
        ).joinToString(separator = "&", prefix = "?")

        return Single.just(url + parameters)
    }
}