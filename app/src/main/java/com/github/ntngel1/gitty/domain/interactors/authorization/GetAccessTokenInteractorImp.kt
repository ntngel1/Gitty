package com.github.ntngel1.gitty.domain.interactors.authorization

import com.github.ntngel1.gitty.domain.gateways.AuthorizationGateway
import io.reactivex.Maybe
import javax.inject.Inject

class GetAccessTokenInteractorImp @Inject constructor(
    private val authorizationGateway: AuthorizationGateway
) : GetAccessTokenInteractor {

    override fun invoke(): Maybe<String> {
        return authorizationGateway.getAccessToken()
    }
}