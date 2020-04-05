package com.github.ntngel1.gitty.domain.entities.repository

data class RepositoryEntity(
    val id: String,
    val name: String,
    val description: String?,
    val languageName: String?,
    val languageColor: String?,
    val forksCount: Int
)