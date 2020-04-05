/*
 * Copyright (c) 5.4.2020
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
import com.github.ntngel1.gitty.domain.interactors.user.get_current_user_login.GetCurrentUserLoginInteractor
import com.github.ntngel1.gitty.domain.interactors.user.get_current_user_login.GetCurrentUserLoginInteractorImp
import com.github.ntngel1.gitty.domain.interactors.user.get_user_overview.GetUserOverviewInteractor
import com.github.ntngel1.gitty.domain.interactors.user.get_user_overview.GetUserOverviewInteractorImp
import com.github.ntngel1.gitty.domain.interactors.user.get_user_profile.GetUserProfileInteractor
import com.github.ntngel1.gitty.domain.interactors.user.get_user_profile.GetUserProfileInteractorImp
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
    }
}