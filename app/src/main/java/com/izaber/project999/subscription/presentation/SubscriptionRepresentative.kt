package com.izaber.project999.subscription.presentation

import com.izaber.project999.core.ClearRepresentative
import com.izaber.project999.core.ProcessDeathHandler
import com.izaber.project999.core.Representative
import com.izaber.project999.core.UiObserver
import com.izaber.project999.dashboard.DashboardRepresentative
import com.izaber.project999.dashboard.DashboardScreen
import com.izaber.project999.main.Navigation
import com.izaber.project999.subscription.domain.SubscriptionInteractor
import com.izaber.project999.utils.UnitFunction

interface SubscriptionRepresentative : Representative<SubscriptionUiState>,
    SaveSubscriptionUiState, SubscriptionObserved, SubscriptionInner {
    fun init(restoreState: SaveAndRestoreSubscriptionUiState.Restore)
    fun subscribe()
    fun finish()

    class Base(
        private val deathHandler: ProcessDeathHandler,
        private val observable: SubscriptionObservable,
        private val clear: ClearRepresentative,
        private val subscribeInteractor: SubscriptionInteractor,
        private val navigation: Navigation.Update
    ) : SubscriptionRepresentative {

        override fun observed() {
            observable.clear()
        }

        override fun init(restoreState: SaveAndRestoreSubscriptionUiState.Restore) {
            if (restoreState.isEmpty()) {
                // init local cache
                deathHandler.firstOpening()
                observable.update(SubscriptionUiState.Initial)
            } else {
                if (deathHandler.wawDeathHappened()) {
                    // go to permanent storage and init localCache
                    deathHandler.deathHandled()
                    restoreState.restore().restoreAfterDeath(this, observable) // todo
                }
            }
        }

        override fun saveState(saveState: SaveAndRestoreSubscriptionUiState.Save) {
            observable.saveState(saveState)
        }

        override fun subscribe() {
            Thread.sleep(10000)
            observable.update(SubscriptionUiState.Loading)
            subscribeInner()
        }

        private val callback: UnitFunction = {
            observable.update(SubscriptionUiState.Success)
        }

        override fun subscribeInner() {
            subscribeInteractor.subscribe(callback)
        }

        override fun finish() {
            clear.clear(DashboardRepresentative::class.java)
            clear.clear(SubscriptionRepresentative::class.java)
            navigation.update(DashboardScreen)
        }

        override fun startGettingUpdates(uiObserver: UiObserver<SubscriptionUiState>) {
            observable.updateObserver(uiObserver)
        }

        override fun stopGettingUpdates() {
            observable.updateObserver()
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