/*
 * Copyright (c) 18.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.di.modules.common

import android.content.Context
import io.noties.markwon.Markwon
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import io.noties.markwon.image.glide.GlideImagesPlugin
import io.noties.markwon.syntax.Prism4jThemeDefault
import io.noties.markwon.syntax.SyntaxHighlightPlugin
import io.noties.prism4j.Prism4j
import io.noties.prism4j.annotations.PrismBundle
import toothpick.config.Module
import toothpick.ktp.binding.bind

@PrismBundle(includeAll = true)
class MarkwonModule(context: Context) : Module() {

    init {
        val prism4j = Prism4j(GrammarLocatorDef())
        val theme = Prism4jThemeDefault.create()

        val markwon = Markwon.builder(context)
            .usePlugin(GlideImagesPlugin.create(context))
            .usePlugin(TaskListPlugin.create(context))
            .usePlugin(TablePlugin.create(context))
            .usePlugin(SyntaxHighlightPlugin.create(prism4j, theme))
            .build()

        bind<Markwon>()
            .toInstance(markwon)
    }
}