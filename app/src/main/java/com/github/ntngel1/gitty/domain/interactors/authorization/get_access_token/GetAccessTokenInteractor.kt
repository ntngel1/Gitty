package com.github.ntngel1.gitty.domain.interactors.authorization.get_access_token

import io.reactivex.Maybe

interface GetAccessTokenInteractor {
    operator fun invoke(): Maybe<String>
}