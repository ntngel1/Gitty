package com.github.ntngel1.gitty.domain.interactors.user.get_current_user_login

import io.reactivex.Single

interface GetCurrentUserLoginInteractor {
    operator fun invoke(): Single<String>
}