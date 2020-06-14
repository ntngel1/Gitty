/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.entities.common

data class Page<T>(
    val hasNextPage: Boolean,
    val cursor: String?,
    val items: List<T>
)