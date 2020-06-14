/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.code

import com.github.ntngel1.gitty.domain.entities.repository.FileTreeEntry
import com.github.ntngel1.gitty.domain.interactors.repository.get_repository_default_branch.GetRepositoryDefaultBranchInteractor
import com.github.ntngel1.gitty.domain.interactors.repository.get_repository_tree.GetRepositoryTreeInteractor
import com.github.ntngel1.gitty.presentation.common.BasePresenter
import com.github.ntngel1.gitty.presentation.di.RepositoryId
import com.github.ntngel1.gitty.presentation.utils.logErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class RepositoryCodePresenter @Inject constructor(
    @RepositoryId private val repositoryId: String,
    private val getRepositoryDefaultBranch: GetRepositoryDefaultBranchInteractor,
    private val getRepositoryTree: GetRepositoryTreeInteractor
) : BasePresenter<RepositoryCodeView>() {

    private var currentState = RepositoryCodeState()
        set(value) {
            field = value
            viewState.setState(value)
        }

    private var repositoryTreeLoadingDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadDefaultBranchTree()
    }

    private fun loadDefaultBranchTree() {
        getRepositoryDefaultBranch(repositoryId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                currentState = currentState.copy(
                    isLoading = true,
                    error = null
                )
            }
            .subscribe({ defaultRepositoryBranch ->
                currentState = currentState.copy(branch = defaultRepositoryBranch)
                loadRepositoryTree()
            }, { throwable ->
                currentState = currentState.copy(isLoading = false, error = throwable)
            })
            .disposeOnDestroy()
    }

    private fun loadRepositoryTree() {
        val currentBranch = currentState.branch
            ?: return

        getRepositoryTree(repositoryId, currentBranch, currentState.path)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                currentState = currentState.copy(
                    isLoading = true,
                    error = null,
                    visibleFileTree = null
                )
            }
            .logErrors()
            .subscribe({ treeEntries ->
                if (currentState.fileTree.isNullOrEmpty()) {
                    currentState = currentState.copy(
                        fileTree = treeEntries,
                        visibleFileTree = treeEntries
                    )
                } else {
                    currentState.fileTree?.findFolderByPath(currentState.path)
                        ?.files = treeEntries
                }

                currentState = currentState.copy(
                    isLoading = false,
                    error = null,
                    visibleFileTree = currentState.fileTree?.findFolderByPath(currentState.path)?.files
                )
            }, { throwable ->
                currentState = currentState.copy(isLoading = false, error = throwable)
            })
            .also { disposable ->
                repositoryTreeLoadingDisposable = disposable
            }
            .disposeOnDestroy()
    }

    private fun List<FileTreeEntry>.findFolderByPath(path: List<String>): FileTreeEntry.Folder {
        return findFolderByPath(
            FileTreeEntry.Folder(id = "", name = "", files = this),
            path
        )
    }

    private tailrec fun findFolderByPath(
        folder: FileTreeEntry.Folder,
        splitPath: List<String>
    ): FileTreeEntry.Folder {
        val nextFolderName = splitPath.firstOrNull()

        return if (!nextFolderName.isNullOrBlank()) {
            val nextFolder = folder.files.filterIsInstance<FileTreeEntry.Folder>()
                .find { it.name == nextFolderName }!!

            findFolderByPath(nextFolder, splitPath.drop(1))
        } else {
            folder
        }
    }

    private fun cancelRepositoryTreeLoading() {
        repositoryTreeLoadingDisposable?.dispose()
        repositoryTreeLoadingDisposable = null
    }

    fun onFileTreeEntryClicked(fileTreeEntry: FileTreeEntry) {
        when (fileTreeEntry) {
            is FileTreeEntry.Folder -> {
                val newPath = currentState.path + fileTreeEntry.name

                currentState = currentState.copy(
                    path = newPath,
                    shouldDisplayBackButton = true
                )

                loadRepositoryTree()
            }
            is FileTreeEntry.Blob -> {
                // TODO Handle blobs
            }
        }
    }

    fun onBackClicked() {
        cancelRepositoryTreeLoading()

        val newPath = currentState.path.dropLast(1)
        currentState.fileTree?.findFolderByPath(newPath)?.files?.let { newVisibleFileTree ->
            currentState = currentState.copy(
                path = newPath,
                visibleFileTree = newVisibleFileTree,
                shouldDisplayBackButton = newPath.isNotEmpty(),
                isLoading = false,
                error = null
            )
        }
    }

    fun onTryAgainClicked() {
        if (currentState.branch != null) {
            loadRepositoryTree()
        } else {
            loadDefaultBranchTree()
        }
    }
}