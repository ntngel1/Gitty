/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.di.modules

import com.github.ntngel1.gitty.presentation.di.UserLogin
import toothpick.config.Module
import toothpick.ktp.binding.bind

class ProfileModule(userLogin: String) : Module() {

    init {
        bind<String>()
            .withName(UserLogin::class)
            .toInstance(userLogin)
    }
}