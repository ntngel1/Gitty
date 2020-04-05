package com.github.ntngel1.gitty.presentation.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.di.Scopes
import com.github.ntngel1.gitty.presentation.ui.Screens
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import toothpick.Toothpick
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private val navigator: Navigator = SupportAppNavigator(
        this,
        supportFragmentManager,
        R.id.main_fragment_container
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.openScope(Scopes.APP_SCOPE)
            .inject(this)

        if (savedInstanceState == null) {
            router.newRootScreen(Screens.Auth)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}
