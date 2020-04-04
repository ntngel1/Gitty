package com.github.ntngel1.gitty.gateway.graphql

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.rxQuery
import com.github.ntngel1.gitty.CurrentUserLoginQuery
import com.github.ntngel1.gitty.domain.gateways.UserGateway
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GraphQLUserGateway @Inject constructor(
    private val apolloClient: ApolloClient
) : UserGateway {

    override fun getCurrentUser() {
        val apolloClient = ApolloClient.builder()
            .build()
    }

    override fun getCurrentUserLogin(): Single<String> {
        val query = CurrentUserLoginQuery()
        return apolloClient.rxQuery(query)
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data() ?: throw IllegalStateException() // TODO Error handling?
            }
            .map {
                it.viewer.login
            }
            .singleOrError()
    }
}