package com.izaber.project999.dashboard

import com.izaber.project999.core.Representative
import com.izaber.project999.core.UiObserver
import com.izaber.project999.main.Navigation
import com.izaber.project999.subscription.presentation.SubscriptionScreen

interface DashboardRepresentative: Representative<PremiumDashboardUiState> {
    fun observed() = Unit
    fun play()

    class Base(
        private val navigation: Navigation.Update
    ): DashboardRepresentative {
        override fun play() {
            navigation.update(SubscriptionScreen)
        }
    }

    class Premium(
        private val observable: PremiumDashboardObservable
    ): DashboardRepresentative {

        override fun observed() {
            observable.clear()
        }

        override fun play() {
            observable.update(PremiumDashboardUiState.Playing)
        }

        override fun startGettingUpdates(uiObserver: UiObserver<PremiumDashboardUiState>) {
            observable.updateObserver(uiObserver)
        }

        override fun stopGettingUpdates() {
            observable.updateObserver()
        }
    }
}