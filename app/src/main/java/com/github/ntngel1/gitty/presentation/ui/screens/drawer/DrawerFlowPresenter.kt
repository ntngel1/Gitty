package com.github.ntngel1.gitty.presentation.ui.screens.drawer

import com.github.ntngel1.gitty.domain.gateways.UserGateway
import com.github.ntngel1.gitty.presentation.base.BasePresenter
import com.github.ntngel1.gitty.presentation.utils.logErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class DrawerFlowPresenter @Inject constructor(
    private val userGateway: UserGateway
) : BasePresenter<DrawerFlowView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        userGateway.getCurrentUserLogin()
            .observeOn(AndroidSchedulers.mainThread())
            .logErrors()
            .subscribe({ currentUserLogin ->
                viewState.showProfileScreen(currentUserLogin)
            }, {

            })
            .disposeOnDestroy()
    }
}