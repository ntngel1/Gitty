package com.github.ntngel1.gitty.domain.gateways

import com.github.ntngel1.gitty.domain.entities.user.ProfileOverviewEntity
import com.github.ntngel1.gitty.domain.entities.user.UserProfileEntity
import io.reactivex.Single

interface UserGateway {
    fun getCurrentUserLogin(): Single<String>
    fun getUserProfile(login: String): Single<UserProfileEntity>
    fun getUserOverview(login: String): Single<ProfileOverviewEntity>
}