/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.gateway.graphql

import android.content.SharedPreferences
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.rx2.rxQuery
import com.github.ntngel1.gitty.*
import com.github.ntngel1.gitty.domain.entities.common.Page
import com.github.ntngel1.gitty.domain.entities.contribution_calendar.ContributionCalendarDayEntity
import com.github.ntngel1.gitty.domain.entities.contribution_calendar.ContributionCalendarEntity
import com.github.ntngel1.gitty.domain.entities.contribution_calendar.ContributionCalendarWeekEntity
import com.github.ntngel1.gitty.domain.entities.gist.GistEntity
import com.github.ntngel1.gitty.domain.entities.user.*
import com.github.ntngel1.gitty.domain.gateways.UserGateway
import com.github.ntngel1.gitty.gateway.utils.emptyListIfNull
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * This is really dirty, I fucked up.
 */
class ApolloUserGateway @Inject constructor(
    private val apolloClient: ApolloClient,
    private val sharedPreferences: SharedPreferences
) : UserGateway {

    override fun getProfileHeader(login: String): Single<ProfileHeaderEntity> {
        val query = UserProfileQuery(
            avatarUrlSize = 200, // TODO Maybe pass as parameter of method?
            login = login
        )

        return Single.defer {
            apolloClient.rxQuery(query)
                .subscribeOn(Schedulers.io()) // TODO pass schedulers via constructor
                .map { response ->
                    response.data()?.user ?: throw IllegalStateException() // TODO Error handling?
                }
                .map { data ->
                    ProfileHeaderEntity(
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
    }

    override fun getCurrentUserLogin(): Single<String> {
        val cachedCurrentUserLogin = sharedPreferences.getString(CURRENT_USER_LOGIN_KEY, null)
        return if (cachedCurrentUserLogin != null) {
            Single.just(cachedCurrentUserLogin)
        } else {
            val query = CurrentUserLoginQuery()
            apolloClient.rxQuery(query)
                .subscribeOn(Schedulers.io()) // TODO pass schedulers via constructor
                .map { response ->
                    response.data() ?: throw IllegalStateException() // TODO Error handling?
                }
                .map { response ->
                    sharedPreferences.edit()
                        .putString(CURRENT_USER_LOGIN_KEY, response.viewer.login)
                        .apply()

                    response.viewer.login
                }
                .singleOrError()
        }
    }

    override fun getProfileOverview(login: String): Single<ProfileOverviewEntity> {
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
                    contributionCalendar = ContributionCalendarEntity(
                        totalContributionCount = user.contributionsCollection.contributionCalendar.totalContributions,
                        colors = user.contributionsCollection.contributionCalendar.colors,
                        weeks = user.contributionsCollection.contributionCalendar.weeks.map { week ->
                            ContributionCalendarWeekEntity(
                                firstDay = week.firstDay,
                                days = week.contributionDays.map { day ->
                                    ContributionCalendarDayEntity(
                                        color = day.color,
                                        contributionCount = day.contributionCount
                                    )
                                }
                            )
                        }
                    ),
                    pinnedItems = pinnedItems.filterNotNull()
                        .map { item ->
                            if (item.__typename == "Gist") {
                                val gist = GistEntity(
                                    id = item.asGist!!.id,
                                    name = item.asGist.name
                                )

                                PinnableItem.Gist(gist)
                            } else {
                                val repository =
                                    PinnedRepositoryEntity(
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

    override fun getRepositories(
        login: String,
        limit: Int,
        cursor: String?
    ): Single<Page<UserRepositoryEntity>> {
        val query = UserRepositoriesQuery(
            login = login,
            limit = limit,
            cursor = Input.optional(cursor)
        )

        return apolloClient.rxQuery(query)
            .subscribeOn(Schedulers.io()) // TODO pass schedulers via constructor
            .map { response ->
                response.data()?.user?.repositories
                    ?: throw IllegalStateException() // TODO Error handling?
            }
            .map { page ->
                Page(
                    hasNextPage = page.pageInfo.hasNextPage,
                    cursor = page.pageInfo.endCursor,
                    items = page.nodes.emptyListIfNull()
                        .filterNotNull()
                        .map { repository ->
                            UserRepositoryEntity(
                                id = repository.fragments.repositoryParts.id,
                                name = repository.fragments.repositoryParts.name,
                                description = repository.fragments.repositoryParts.description,
                                forksCount = repository.fragments.repositoryParts.forkCount,
                                starsCount = repository.fragments.repositoryParts.stargazers.totalCount,
                                updatedAt = repository.fragments.repositoryParts.updatedAt,
                                forkedFromRepositoryName = repository.fragments.repositoryParts.parent?.name,
                                forkedFromRepositoryOwner = repository.fragments.repositoryParts.parent?.owner?.login,
                                licenseName = repository.fragments.repositoryParts.licenseInfo?.nickname,
                                languageName = repository.fragments.repositoryParts.primaryLanguage?.name,
                                languageColor = repository.fragments.repositoryParts.primaryLanguage?.color
                            )
                        }
                )
            }
            .singleOrError()
    }

    override fun getStarredRepositories(
        login: String,
        limit: Int,
        cursor: String?
    ): Single<Page<UserRepositoryEntity>> {
        val query = UserStarredRepositoriesQuery(
            login = login,
            limit = limit,
            cursor = Input.optional(cursor)
        )

        return apolloClient.rxQuery(query)
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data()?.user?.starredRepositories
                    ?: throw IllegalStateException() // TODO Error handling?
            }
            .map { page ->
                Page(
                    hasNextPage = page.pageInfo.hasNextPage,
                    cursor = page.pageInfo.endCursor,
                    items = page.nodes.emptyListIfNull()
                        .filterNotNull()
                        .map { repository ->
                            UserRepositoryEntity(
                                id = repository.fragments.repositoryParts.id,
                                name = repository.fragments.repositoryParts.name,
                                description = repository.fragments.repositoryParts.description,
                                forksCount = repository.fragments.repositoryParts.forkCount,
                                starsCount = repository.fragments.repositoryParts.stargazers.totalCount,
                                updatedAt = repository.fragments.repositoryParts.updatedAt,
                                forkedFromRepositoryName = repository.fragments.repositoryParts.parent?.name,
                                forkedFromRepositoryOwner = repository.fragments.repositoryParts.parent?.owner?.login,
                                licenseName = repository.fragments.repositoryParts.licenseInfo?.nickname,
                                languageName = repository.fragments.repositoryParts.primaryLanguage?.name,
                                languageColor = repository.fragments.repositoryParts.primaryLanguage?.color
                            )
                        }
                )
            }
            .singleOrError()
    }

    companion object {
        private const val CURRENT_USER_LOGIN_KEY = "current_user_login"
    }
}