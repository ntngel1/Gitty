/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.base.BaseFragment
import com.github.ntngel1.gitty.presentation.ui.Screens
import com.github.ntngel1.gitty.presentation.utils.gone
import com.github.ntngel1.gitty.presentation.utils.toast
import com.github.ntngel1.gitty.presentation.utils.visible
import kotlinx.android.synthetic.main.fragment_auth.*
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AuthFragment : BaseFragment(), AuthView {

    override val layoutId: Int
        get() = R.layout.fragment_auth

    @Inject
    lateinit var router: Router

    private val presenter by moxyPresenter {
        scope.getInstance(AuthPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scope.inject(this)

        setupIntro()
        setupWebView()
    }

    override fun setIntro() {
        listOf<View>(
            progressbar_auth,
            webview_auth
        ).gone()

        listOf<View>(
            imageview_auth_logo,
            textview_auth_authorize,
            button_auth_authorize
        ).visible()
    }

    override fun setLoading() {
        listOf<View>(
            imageview_auth_logo,
            textview_auth_authorize,
            button_auth_authorize,
            webview_auth
        ).gone()

        progressbar_auth.visible()
    }

    override fun setWebView(authorizationUrl: String) {
        listOf<View>(
            imageview_auth_logo,
            textview_auth_authorize,
            button_auth_authorize,
            progressbar_auth
        ).gone()

        webview_auth.visible()
        webview_auth.loadUrl(authorizationUrl)
    }

    override fun showMainScreen() {
        router.newRootScreen(Screens.DrawerFlow)
    }

    override fun showError(throwable: Throwable) {
        throwable.localizedMessage?.let { message ->
            requireContext().toast(message)
        }
    }

    private fun setupIntro() {
        button_auth_authorize.setOnClickListener {
            presenter.onAuthorizeClicked()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webview_auth.settings.javaScriptEnabled = true
        webview_auth.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                request?.url?.let { url ->
                    if (url.host == "localhost") {
                        presenter.onAcceptRedirectUrl(url.toString())
                        return false
                    }
                }

                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }
}