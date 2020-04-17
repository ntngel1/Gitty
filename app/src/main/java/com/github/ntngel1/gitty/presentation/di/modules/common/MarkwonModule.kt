/*
 * Copyright (c) 17.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.di.modules.common

import android.content.Context
import io.noties.markwon.Markwon
import toothpick.config.Module
import toothpick.ktp.binding.bind

class MarkwonModule(context: Context) : Module() {

    init {
        val markwon = Markwon.create(context)

        bind<Markwon>()
            .toInstance(markwon)
    }
}