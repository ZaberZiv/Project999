package com.izaber.project999.main

import com.izaber.project999.core.Representative
import com.izaber.project999.core.UiObserver
import com.izaber.project999.dashboard.DashboardScreen

interface MainRepresentative : Representative<Screen> {
    fun showDashboard(firstTime: Boolean)

    class Base(
        private val navigation: Navigation.Mutable
    ) : MainRepresentative {

        override fun startGettingUpdates(uiObserver: UiObserver<Screen>) {
            navigation.updateObserver(uiObserver)
        }

        override fun stopGettingUpdates() {
            navigation.updateObserver()
        }

        override fun showDashboard(firstTime: Boolean) {
            if (firstTime) navigation.update(DashboardScreen)
        }
    }
}

