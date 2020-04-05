/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.gateway.graphql

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.rxQuery
import com.github.ntngel1.gitty.CurrentUserLoginQuery
import com.github.ntngel1.gitty.UserOverviewQuery
import com.github.ntngel1.gitty.UserProfileQuery
import com.github.ntngel1.gitty.domain.entities.gist.GistEntity
import com.github.ntngel1.gitty.domain.entities.repository.RepositoryEntity
import com.github.ntngel1.gitty.domain.entities.user.PinnableItem
import com.github.ntngel1.gitty.domain.entities.user.ProfileOverviewEntity
import com.github.ntngel1.gitty.domain.entities.user.UserProfileEntity
import com.github.ntngel1.gitty.domain.entities.user.UserStatusEntity
import com.github.ntngel1.gitty.domain.gateways.UserGateway
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GraphQLUserGateway @Inject constructor(
    private val apolloClient: ApolloClient
) : UserGateway {

    override fun getUserProfile(login: String): Single<UserProfileEntity> {
        val query = UserProfileQuery(
            avatarUrlSize = 200, // TODO Maybe pass as parameter of method?
            login = login
        )

        return apolloClient.rxQuery(query)
            .subscribeOn(Schedulers.io()) // TODO pass schedulers via constructor
            .map { response ->
                response.data()?.user ?: throw IllegalStateException() // TODO Error handling?
            }
            .map { data ->
                UserProfileEntity(
                    login = data.login,
                    name = data.name,
                    avatarUrl = data.avatarUrl,
                    repositoriesCount = data.repositories.totalCount,
                    projectsCount = data.projects.totalCount,
                    followersCount = data.followers.totalCount,
                    followingCount = data.following.totalCount,
                    starredRepositoriesCount = data.starredRepositories.totalCount,
                    status = UserStatusEntity(
                        message = data.status?.message,
                        emoji = data.status?.emoji
                    )
                )
            }
            .singleOrError()
    }

    override fun getCurrentUserLogin(): Single<String> {
        val query = CurrentUserLoginQuery()
        return apolloClient.rxQuery(query)
            .subscribeOn(Schedulers.io()) // TODO pass schedulers via constructor
            .map { response ->
                response.data() ?: throw IllegalStateException() // TODO Error handling?
            }
            .map {
                it.viewer.login
            }
            .singleOrError()
    }

    override fun getUserOverview(login: String): Single<ProfileOverviewEntity> {
        val query = UserOverviewQuery(login)
        return apolloClient.rxQuery(query)
            .subscribeOn(Schedulers.io()) // TODO pass schedulers via constructor
            .map { response ->
                response.data()?.user ?: throw IllegalStateException() // TODO Error handling?
            }
            .map { user ->
                val pinnedItems = user.pinnedItems.nodes
                    ?: emptyList()

                ProfileOverviewEntity(
                    pinnedItems = pinnedItems.filterNotNull()
                        .map { item ->
                            if (item.__typename == "Gist") {
                                val gist = GistEntity(
                                    id = item.asGist!!.id,
                                    name = item.asGist.name
                                )

                                PinnableItem.Gist(gist)
                            } else {
                                val repository = RepositoryEntity(
                                    id = item.asRepository!!.id,
                                    name = item.asRepository.name,
                                    description = item.asRepository.description,
                                    languageName = item.asRepository.languages?.nodes?.firstOrNull()?.name,
                                    languageColor = item.asRepository.languages?.nodes?.firstOrNull()?.color,
                                    forksCount = item.asRepository.forks.totalCount
                                )

                                PinnableItem.Repository(repository)
                            }
                        }
                )
            }
            .singleOrError()
    }
}