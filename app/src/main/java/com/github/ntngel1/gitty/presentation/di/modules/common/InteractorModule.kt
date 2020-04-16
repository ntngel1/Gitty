/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.di.modules.common

import com.github.ntngel1.gitty.domain.interactors.authorization.authorize.AuthorizeInteractor
import com.github.ntngel1.gitty.domain.interactors.authorization.authorize.AuthorizeInteractorImp
import com.github.ntngel1.gitty.domain.interactors.authorization.get_access_token.GetAccessTokenInteractor
import com.github.ntngel1.gitty.domain.interactors.authorization.get_access_token.GetAccessTokenInteractorImp
import com.github.ntngel1.gitty.domain.interactors.authorization.get_authorization_url.GetAuthorizationUrlInteractor
import com.github.ntngel1.gitty.domain.interactors.authorization.get_authorization_url.GetAuthorizationUrlInteractorImp
import com.github.ntngel1.gitty.domain.interactors.authorization.is_authorized.IsAuthorizedInteractor
import com.github.ntngel1.gitty.domain.interactors.authorization.is_authorized.IsAuthorizedInteractorImp
import com.github.ntngel1.gitty.domain.interactors.repository.get_repository_header.GetRepositoryHeaderInteractor
import com.github.ntngel1.gitty.domain.interactors.repository.get_repository_header.GetRepositoryHeaderInteractorImp
import com.github.ntngel1.gitty.domain.interactors.repository.get_repository_overview.GetRepositoryOverviewInteractor
import com.github.ntngel1.gitty.domain.interactors.repository.get_repository_overview.GetRepositoryOverviewInteractorImp
import com.github.ntngel1.gitty.domain.interactors.user.get_current_user_login.GetCurrentUserLoginInteractor
import com.github.ntngel1.gitty.domain.interactors.user.get_current_user_login.GetCurrentUserLoginInteractorImp
import com.github.ntngel1.gitty.domain.interactors.user.get_user_overview.GetUserOverviewInteractor
import com.github.ntngel1.gitty.domain.interactors.user.get_user_overview.GetUserOverviewInteractorImp
import com.github.ntngel1.gitty.domain.interactors.user.get_user_profile_header.GetUserProfileInteractor
import com.github.ntngel1.gitty.domain.interactors.user.get_user_profile_header.GetUserProfileInteractorImp
import com.github.ntngel1.gitty.domain.interactors.user.get_user_repositories.GetUserRepositoriesInteractor
import com.github.ntngel1.gitty.domain.interactors.user.get_user_repositories.GetUserRepositoriesInteractorImp
import com.github.ntngel1.gitty.domain.interactors.user.get_user_starred_repositories.GetUserStarredRepositoriesInteractor
import com.github.ntngel1.gitty.domain.interactors.user.get_user_starred_repositories.GetUserStarredRepositoriesInteractorImp
import toothpick.config.Module
import toothpick.ktp.binding.bind

class InteractorModule : Module() {

    init {
        bind<AuthorizeInteractor>()
            .toClass<AuthorizeInteractorImp>()

        bind<GetAuthorizationUrlInteractor>()
            .toClass<GetAuthorizationUrlInteractorImp>()

        bind<GetAccessTokenInteractor>()
            .toClass<GetAccessTokenInteractorImp>()

        bind<GetCurrentUserLoginInteractor>()
            .toClass<GetCurrentUserLoginInteractorImp>()

        bind<GetUserProfileInteractor>()
            .toClass<GetUserProfileInteractorImp>()

        bind<GetUserOverviewInteractor>()
            .toClass<GetUserOverviewInteractorImp>()

        bind<IsAuthorizedInteractor>()
            .toClass<IsAuthorizedInteractorImp>()

        bind<GetUserRepositoriesInteractor>()
            .toClass<GetUserRepositoriesInteractorImp>()

        bind<GetUserStarredRepositoriesInteractor>()
            .toClass<GetUserStarredRepositoriesInteractorImp>()

        bind<GetRepositoryOverviewInteractor>()
            .toClass<GetRepositoryOverviewInteractorImp>()

        bind<GetRepositoryHeaderInteractor>()
            .toClass<GetRepositoryHeaderInteractorImp>()
    }
}