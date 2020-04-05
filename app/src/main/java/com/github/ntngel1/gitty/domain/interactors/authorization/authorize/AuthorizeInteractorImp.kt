package com.github.ntngel1.gitty.domain.interactors.authorization.authorize

import com.github.ntngel1.gitty.domain.gateways.AuthorizationGateway
import io.reactivex.Completable
import java.net.URI
import javax.inject.Inject

class AuthorizeInteractorImp @Inject constructor(
    private val authorizationGateway: AuthorizationGateway
) : AuthorizeInteractor {

    // TODO Refactor
    override fun invoke(redirectUrl: String): Completable {
        val parameters = URI.create(redirectUrl).toURL().query
            .split("&")
            .map { parameter ->
                val parts = parameter.split("=")
                parts[0] to parts[1]
            }
            .groupBy { (name, _) -> name }

        val code = (parameters["code"] ?: error("")).first().second
        val state = (parameters["state"] ?: error("")).first().second

        return authorizationGateway.createAccessToken(
            code = code,
            state = state,
            clientId = "69e1406fe0872dcb006f",
            clientSecret = "635c07020d2738d9689463f3e277caca325a64ef",
            redirectUri = "https://localhost:1234/auth"
        ).flatMapCompletable { accessToken ->
            authorizationGateway.saveAccessToken(accessToken)
        }
    }
}