/*
 * Copyright (c) 28.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.entities.repository

/**
 * Represents folders and files in repository.
 */
sealed class FileTreeEntry {

    abstract val id: String
    abstract val name: String

    data class Blob(
        override val id: String,
        override val name: String,
        val byteSize: Int
    ) : FileTreeEntry()

    data class Folder(
        override val id: String,
        override val name: String,
        var files: List<FileTreeEntry> = emptyList() // Dirty place, using 'var' instead of 'val'
    ) : FileTreeEntry()
}