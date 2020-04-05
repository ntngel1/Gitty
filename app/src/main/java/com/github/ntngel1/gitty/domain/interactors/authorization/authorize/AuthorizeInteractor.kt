package com.github.ntngel1.gitty.domain.interactors.authorization.authorize

import io.reactivex.Completable

interface AuthorizeInteractor {
    operator fun invoke(redirectUrl: String): Completable
}