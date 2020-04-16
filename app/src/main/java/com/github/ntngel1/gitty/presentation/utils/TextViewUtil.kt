/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.utils

import android.graphics.drawable.Drawable
import android.widget.TextView
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition

var TextView.drawableLeft: Drawable?
    get() = this.compoundDrawables[0]
    set(value) {
        setCompoundDrawablesWithIntrinsicBounds(
            value,
            drawableTop,
            drawableRight,
            drawableBottom
        )
    }

var TextView.drawableTop: Drawable?
    get() = this.compoundDrawables[1]
    set(value) {
        setCompoundDrawablesWithIntrinsicBounds(
            drawableLeft,
            value,
            drawableRight,
            drawableBottom
        )
    }

var TextView.drawableRight: Drawable?
    get() = this.compoundDrawables[2]
    set(value) {
        setCompoundDrawablesWithIntrinsicBounds(
            drawableLeft,
            drawableTop,
            value,
            drawableBottom
        )
    }

var TextView.drawableBottom: Drawable?
    get() = this.compoundDrawables[3]
    set(value) {
        setCompoundDrawablesWithIntrinsicBounds(
            drawableLeft,
            drawableTop,
            drawableRight,
            value
        )
    }

val TextView.glideDrawableLeft
    get() = object : CustomViewTarget<TextView, Drawable>(this) {
        override fun onLoadFailed(errorDrawable: Drawable?) {
            errorDrawable?.let { drawable ->
                view.drawableLeft = drawable
            }
        }

        override fun onResourceCleared(placeholder: Drawable?) {
            placeholder?.let { drawable ->
                view.drawableLeft = drawable
            }
        }

        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            // TODO Apply transitions
            view.drawableLeft = resource
        }
    }