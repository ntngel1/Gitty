package com.github.ntngel1.gitty.domain.interactors.authorization

import io.reactivex.Single

interface GetAuthorizationUrlInteractor {
    operator fun invoke(): Single<String>
}