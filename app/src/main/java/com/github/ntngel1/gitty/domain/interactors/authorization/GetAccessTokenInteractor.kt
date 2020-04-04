package com.github.ntngel1.gitty.domain.interactors.authorization

import io.reactivex.Maybe

interface GetAccessTokenInteractor {
    operator fun invoke(): Maybe<String>
}