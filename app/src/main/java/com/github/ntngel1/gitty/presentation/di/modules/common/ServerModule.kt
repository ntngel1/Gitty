/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.di.modules.common

import com.apollographql.apollo.ApolloClient
import com.github.ntngel1.gitty.gateway.graphql.AuthorizationInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import toothpick.config.Module
import toothpick.ktp.binding.bind

class ServerModule : Module() {

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

        val gson = GsonBuilder().create()

        val apolloClient = ApolloClient.builder()
            .okHttpClient(okHttpClient)
            .serverUrl("https://api.github.com/graphql")
            .build()

        bind<Gson>().toInstance(gson)
        bind<OkHttpClient>().toInstance(okHttpClient)
        bind<ApolloClient>().toInstance(apolloClient)
    }
}