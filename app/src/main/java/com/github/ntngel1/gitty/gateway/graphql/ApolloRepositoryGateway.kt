/*
 * Copyright (c) 28.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.gateway.graphql

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.rxMutate
import com.apollographql.apollo.rx2.rxQuery
import com.github.ntngel1.gitty.*
import com.github.ntngel1.gitty.domain.entities.repository.FileTreeEntry
import com.github.ntngel1.gitty.domain.entities.repository.RepositoryHeaderEntity
import com.github.ntngel1.gitty.domain.entities.repository.RepositoryOverviewEntity
import com.github.ntngel1.gitty.domain.entities.repository.RepositorySubscription
import com.github.ntngel1.gitty.domain.gateways.RepositoryGateway
import com.github.ntngel1.gitty.domain.interactors.repository.star_repository.StarRepositoryInteractor
import com.github.ntngel1.gitty.domain.interactors.repository.unstar_repository.UnstarRepositoryInteractor
import com.github.ntngel1.gitty.type.SubscriptionState
import io.reactivex.Maybe
import io.reactivex.Observable
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
                    forkedFromRepositoryOwnerName = repository.parent?.owner?.login,
                    forkedFromRepositoryId = repository.parent?.id,
                    description = repository.description,
                    isCurrentUserStarredRepo = repository.viewerHasStarred,
                    isCurrentUserAdmin = repository.viewerCanAdminister,
                    defaultBranchName = repository.defaultBranchRef?.name,
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

    override fun starRepository(id: String): Single<StarRepositoryInteractor.StarResult> {
        val mutation = StarRepositoryMutation(
            starrableId = id
        )

        return apolloClient.rxMutate(mutation)
            .observeOn(Schedulers.io())
            .map { response ->
                response.data()?.addStar?.starrable?.asRepository ?: error("TODO Error handling")
            }
            .map { repository ->
                StarRepositoryInteractor.StarResult(
                    isRepositoryStarred = repository.viewerHasStarred,
                    starsCount = repository.stargazers.totalCount
                )
            }
    }

    override fun unstarRepository(id: String): Single<UnstarRepositoryInteractor.UnstarResult> {
        val mutation = UnstarRepositoryMutation(
            starrableId = id
        )

        return apolloClient.rxMutate(mutation)
            .observeOn(Schedulers.io())
            .map { response ->
                response.data()?.removeStar?.starrable?.asRepository ?: error("TODO Error handling")
            }
            .map { repository ->
                UnstarRepositoryInteractor.UnstarResult(
                    isRepositoryStarred = repository.viewerHasStarred,
                    starsCount = repository.stargazers.totalCount
                )
            }
    }

    override fun getRepositoryReadmeMd(
        id: String,
        lowercaseExpression: String,
        uppercaseExpression: String
    ): Maybe<String> {
        val query = RepositoryReadmeMdQuery(
            id = id,
            lowercaseExpression = lowercaseExpression,
            uppercaseExpression = uppercaseExpression
        )

        return apolloClient.rxQuery(query)
            .subscribeOn(Schedulers.io())
            .singleOrError()
            .flatMapMaybe { response ->
                val data = response.data()?.node?.asRepository
                val readmeMdText = data?.uppercaseObject?.asBlob?.text
                    ?: data?.lowercaseObject?.asBlob1?.text

                if (readmeMdText != null) {
                    Maybe.just(readmeMdText)
                } else {
                    Maybe.empty()
                }
            }
    }

    override fun getRepositoryTree(
        id: String,
        branchName: String,
        path: String
    ): Single<List<FileTreeEntry>> {
        val expression = "$branchName:$path"
        val query = RepositoryTreeQuery(
            id = id,
            expression = expression
        )

        return apolloClient.rxQuery(query)
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data()?.node?.asRepository?.object_?.asTree?.entries
                    ?: error("TODO Error handling")
            }
            .flatMap { entries ->
                Observable.fromIterable(entries)
            }
            .map { entry ->
                entry.object_?.asBlob?.let { blob ->
                    FileTreeEntry.Blob(
                        name = entry.name,
                        id = entry.oid,
                        byteSize = blob.byteSize
                    )
                } ?: FileTreeEntry.Folder(
                    id = entry.oid,
                    name = entry.name
                )
            }
            .toList()
    }
}