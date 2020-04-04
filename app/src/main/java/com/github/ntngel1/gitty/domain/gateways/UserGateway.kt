package com.github.ntngel1.gitty.domain.gateways

import io.reactivex.Single

interface UserGateway {
    fun getCurrentUserLogin(): Single<String>
    fun getCurrentUser()
}