package com.github.ntngel1.gitty.presentation

import android.app.Application
import com.github.ntngel1.gitty.BuildConfig
import com.github.ntngel1.gitty.presentation.di.Scopes
import com.github.ntngel1.gitty.presentation.di.modules.common.AppModule
import com.github.ntngel1.gitty.presentation.di.modules.common.GatewayModule
import com.github.ntngel1.gitty.presentation.di.modules.common.InteractorModule
import com.github.ntngel1.gitty.presentation.di.modules.common.ServerModule
import timber.log.Timber
import toothpick.Toothpick
import toothpick.configuration.Configuration

class GittyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        initTimber()
        initToothpick()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction())
        }

        Toothpick.openScope(Scopes.APP_SCOPE)
            .installModules(
                AppModule(
                    this
                )
            )
            .openSubScope(Scopes.INTERACTOR_SCOPE)
            .installModules(
                ServerModule(),
                GatewayModule(),
                InteractorModule()
            )
    }

    companion object {
        lateinit var INSTANCE: GittyApplication
            private set
    }
}