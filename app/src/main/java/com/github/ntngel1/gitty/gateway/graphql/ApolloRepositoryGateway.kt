/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.gateway.graphql

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.rxQuery
import com.github.ntngel1.gitty.RepositoryHeaderQuery
import com.github.ntngel1.gitty.RepositoryOverviewQuery
import com.github.ntngel1.gitty.domain.entities.repository.RepositoryHeaderEntity
import com.github.ntngel1.gitty.domain.entities.repository.RepositoryOverviewEntity
import com.github.ntngel1.gitty.domain.entities.repository.RepositorySubscription
import com.github.ntngel1.gitty.domain.gateways.RepositoryGateway
import com.github.ntngel1.gitty.type.SubscriptionState
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ApolloRepositoryGateway @Inject constructor(
    private val apolloClient: ApolloClient
) : RepositoryGateway {

    override fun getRepositoryOverview(id: String): Single<RepositoryOverviewEntity> {
        val query = RepositoryOverviewQuery(id)

        return apolloClient.rxQuery(query)
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data()?.node?.asRepository ?: error("TODO Handle error")
            }
            .map { repository ->
                RepositoryOverviewEntity(
                    forksCount = repository.forkCount,
                    starsCount = repository.stargazers.totalCount,
                    watchersCount = repository.watchers.totalCount,
                    ownerName = repository.owner.login,
                    ownerAvatarUrl = repository.owner.avatarUrl,
                    forkedFromRepositoryName = repository.parent?.name,
                    forkedFromOwnerName = repository.parent?.owner?.login,
                    description = repository.description,
                    isCurrentUserStarredRepo = repository.viewerHasStarred,
                    isCurrentUserAdmin = repository.viewerCanAdminister,
                    currentUserSubscriptionStatus = when (repository.viewerSubscription) {
                        SubscriptionState.IGNORED -> RepositorySubscription.IGNORED
                        SubscriptionState.SUBSCRIBED -> RepositorySubscription.SUBSCRIBED
                        SubscriptionState.UNSUBSCRIBED -> RepositorySubscription.UNSUBSCRIBED
                        else -> RepositorySubscription.UNKNOWN
                    }
                )
            }
            .singleOrError()
    }

    override fun getRepositoryHeader(id: String): Single<RepositoryHeaderEntity> {
        val query = RepositoryHeaderQuery(id)

        return apolloClient.rxQuery(query)
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data()?.node?.asRepository ?: error("TODO Error handling")
            }
            .map { repository ->
                RepositoryHeaderEntity(
                    issuesCount = repository.issues.totalCount,
                    projectsCount = repository.projects.totalCount,
                    pullRequestsCount = repository.pullRequests.totalCount,
                    isCurrentUserAdmin = repository.viewerCanAdminister
                )
            }
            .singleOrError()
    }
}