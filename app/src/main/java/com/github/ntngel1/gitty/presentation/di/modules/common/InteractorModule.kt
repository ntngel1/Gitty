package com.github.ntngel1.gitty.presentation.di.modules.common

import com.github.ntngel1.gitty.domain.interactors.authorization.*
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
    }
}