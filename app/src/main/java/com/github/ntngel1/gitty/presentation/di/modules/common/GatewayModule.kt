/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.di.modules.common

import com.github.ntngel1.gitty.domain.gateways.AuthorizationGateway
import com.github.ntngel1.gitty.domain.gateways.UserGateway
import com.github.ntngel1.gitty.gateway.graphql.GraphQLUserGateway
import com.github.ntngel1.gitty.gateway.rest.gateways.RestAuthorizationGateway
import toothpick.config.Module
import toothpick.ktp.binding.bind

class GatewayModule : Module() {

    init {
        bind<AuthorizationGateway>()
            .toClass<RestAuthorizationGateway>()

        bind<UserGateway>()
            .toClass<GraphQLUserGateway>()
    }
}