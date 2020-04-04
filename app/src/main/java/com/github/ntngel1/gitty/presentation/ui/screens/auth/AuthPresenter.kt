package com.github.ntngel1.gitty.presentation.ui.screens.auth

import com.github.ntngel1.gitty.domain.interactors.authorization.AuthorizeInteractor
import com.github.ntngel1.gitty.domain.interactors.authorization.GetAuthorizationUrlInteractor
import com.github.ntngel1.gitty.presentation.base.BasePresenter
import com.github.ntngel1.gitty.presentation.utils.logErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class AuthPresenter @Inject constructor(
    private val getAuthorizationUrl: GetAuthorizationUrlInteractor,
    private val authorize: AuthorizeInteractor
) : BasePresenter<AuthView>() {

    fun onAuthorizeClicked() {
        getAuthorizationUrl()
            .doOnSubscribe {
                viewState.setLoading()
            }
            .logErrors()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { authorizationUrl ->
                    viewState.setWebView(authorizationUrl)
                },
                onError = { throwable ->
                    viewState.setIntro()
                    viewState.showError(throwable)
                }
            )
            .disposeOnDestroy()
    }

    fun onAcceptRedirectUrl(redirectUrl: String) {
        authorize(redirectUrl)
            .doOnSubscribe {
                viewState.setLoading()
            }
            .logErrors()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    viewState.showMainScreen()
                },
                onError = { throwable ->
                    viewState.setIntro()
                    viewState.showError(throwable)
                }
            )
            .disposeOnDestroy()
    }
}