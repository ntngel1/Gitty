package com.github.ntngel1.gitty.domain.interactors.authorization.get_access_token

import com.github.ntngel1.gitty.domain.gateways.AuthorizationGateway
import com.github.ntngel1.gitty.domain.interactors.authorization.get_access_token.GetAccessTokenInteractor
import io.reactivex.Maybe
import javax.inject.Inject

class GetAccessTokenInteractorImp @Inject constructor(
    private val authorizationGateway: AuthorizationGateway
) : GetAccessTokenInteractor {

    override fun invoke(): Maybe<String> {
        return authorizationGateway.getAccessToken()
    }
}