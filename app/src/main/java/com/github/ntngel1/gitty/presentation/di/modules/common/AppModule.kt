/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.di.modules.common

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module
import toothpick.ktp.binding.bind

class AppModule(context: Context) : Module() {

    init {
        bind<Context>().toInstance(context)
        bind<SharedPreferences>().toInstance {
            context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        }

        val cicerone = Cicerone.create()
        bind<Router>().toInstance(cicerone.router)
        bind<NavigatorHolder>().toInstance(cicerone.navigatorHolder)

        bind<Scheduler>().toInstance(AndroidSchedulers.mainThread())
    }
}