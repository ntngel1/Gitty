/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.gateway.graphql

import com.github.ntngel1.gitty.domain.interactors.authorization.get_access_token.GetAccessTokenInteractor
import com.github.ntngel1.gitty.presentation.di.Scopes
import okhttp3.Interceptor
import okhttp3.Response
import toothpick.Toothpick

class AuthorizationInterceptor : Interceptor {

    private val getAccessToken by lazy {
        Toothpick.openScope(Scopes.INTERACTOR_SCOPE)
            .getInstance(GetAccessTokenInteractor::class.java)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = getAccessToken().blockingGet()?.let { accessToken ->
            chain.request()
                .newBuilder()
                .addHeader("Authorization", "token $accessToken")
                .build()
        } ?: chain.request()

        return chain.proceed(request)
    }
}