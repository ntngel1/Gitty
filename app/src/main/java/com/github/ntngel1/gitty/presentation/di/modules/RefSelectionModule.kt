/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.di.modules

import com.github.ntngel1.gitty.presentation.di.RefName
import toothpick.config.Module
import toothpick.ktp.binding.bind

class RefSelectionModule(selectedRef: String) : Module() {

    init {
        bind<String>()
            .withName(RefName::class)
            .toInstance(selectedRef)
    }
}