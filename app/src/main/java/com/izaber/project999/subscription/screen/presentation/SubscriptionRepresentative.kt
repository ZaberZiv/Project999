package com.izaber.project999.subscription.screen.presentation

import com.izaber.project999.core.HandleDeath
import com.izaber.project999.core.Representative
import com.izaber.project999.core.UiObserver
import com.izaber.project999.dashboard.DashboardScreen
import com.izaber.project999.main.Navigation
import com.izaber.project999.subscription.progress.presentation.ComeBack
import com.izaber.project999.subscription.progress.presentation.Observed
import com.izaber.project999.subscription.progress.presentation.Subscribe

interface SubscriptionRepresentative : Representative<SubscriptionUiState>,
    SaveSubscriptionUiState, Observed, ComeBack<Boolean>, Subscribe {
    fun init(restoreState: SaveAndRestoreSubscriptionUiState.Restore)
    fun finish()

    class Base(
        private val handleDeath: HandleDeath,
        private val observable: SubscriptionObservable,
        private val clear: () -> Unit,
        private val navigation: Navigation.Update
    ) : SubscriptionRepresentative {

        override fun observed() {
            observable.clear()
        }

        override fun init(restoreState: SaveAndRestoreSubscriptionUiState.Restore) {
            if (restoreState.isEmpty()) {
                // init local cache
                handleDeath.firstOpening()
                observable.update(SubscriptionUiState.Initial)
            } else {
                if (handleDeath.didDeathHappened()) {
                    // go to permanent storage and init localCache
                    handleDeath.deathHandled()
                    restoreState.restore().restoreAfterDeath(observable) // todo
                }
            }
        }

        override fun saveState(saveState: SaveAndRestoreSubscriptionUiState.Save) {
            observable.saveState(saveState)
        }

        override fun subscribe() {
            observable.update(SubscriptionUiState.Loading)
        }

        override fun finish() {
            navigation.update(DashboardScreen)
            clear.invoke()
        }

        override fun startGettingUpdates(uiObserver: UiObserver<SubscriptionUiState>) {
            observable.updateObserver(uiObserver)
        }

        override fun stopGettingUpdates() {
            observable.updateObserver(EmptySubscriptionObserver)
        }

        override fun comeback(data: Boolean) {
            if (data) finish()
        }
    }
}

interface SaveSubscriptionUiState {
    fun saveState(saveState: SaveAndRestoreSubscriptionUiState.Save)
}

interface SubscriptionObserved {
    fun observed()
}

interface SubscriptionInner {
    fun subscribeInner()
}