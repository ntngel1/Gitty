package com.github.ntngel1.gitty.domain.interactors.user.get_user_overview

import com.github.ntngel1.gitty.domain.entities.user.ProfileOverviewEntity
import io.reactivex.Single

interface GetUserOverviewInteractor {
    operator fun invoke(login: String): Single<ProfileOverviewEntity>
}