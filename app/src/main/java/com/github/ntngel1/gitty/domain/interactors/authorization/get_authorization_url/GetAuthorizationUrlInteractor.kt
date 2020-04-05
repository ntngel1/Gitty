package com.github.ntngel1.gitty.domain.interactors.authorization.get_authorization_url

import io.reactivex.Single

interface GetAuthorizationUrlInteractor {
    operator fun invoke(): Single<String>
}