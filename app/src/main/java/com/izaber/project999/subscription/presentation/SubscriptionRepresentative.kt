package com.izaber.project999.subscription.presentation

import com.izaber.project999.core.ClearRepresentative
import com.izaber.project999.core.ProcessHandleDeath
import com.izaber.project999.core.Representative
import com.izaber.project999.core.RunAsync
import com.izaber.project999.core.UiObserver
import com.izaber.project999.dashboard.DashboardScreen
import com.izaber.project999.main.Navigation
import com.izaber.project999.subscription.domain.SubscriptionInteractor
import com.izaber.project999.subscription.domain.SubscriptionResult

interface SubscriptionRepresentative : Representative<SubscriptionUiState>,
    SaveSubscriptionUiState, SubscriptionObserved, SubscriptionInner {
    fun init(restoreState: SaveAndRestoreSubscriptionUiState.Restore)
    fun subscribe()
    suspend fun subscribeInternal()
    fun finish()
    fun comeback()

    class Base(
        private val runAsync: RunAsync,
        private val handleDeath: ProcessHandleDeath,
        private val observable: SubscriptionObservable,
        private val clear: ClearRepresentative,
        private val subscribeInteractor: SubscriptionInteractor,
        private val navigation: Navigation.Update,
        private val mapper: SubscriptionResult.Mapper
    ) : Representative.Abstract<SubscriptionUiState>(runAsync),
        SubscriptionRepresentative {

        private var canGoBack: Boolean = true

        private val uiBlock: (SubscriptionResult) -> Unit = { result ->
            result.map(mapper) { canGoBack = it }
        }

        override fun observed() {
            observable.clear()
        }

        override fun init(restoreState: SaveAndRestoreSubscriptionUiState.Restore) {
            if (restoreState.isEmpty()) {
                // init local cache
                handleDeath.firstOpening()
                observable.update(SubscriptionUiState.Initial)
            } else {
                if (handleDeath.wawDeathHappened()) {
                    // go to permanent storage and init localCache
                    handleDeath.deathHandled()
                    restoreState.restore().restoreAfterDeath(this, observable) // todo
                }
            }
        }

        override fun saveState(saveState: SaveAndRestoreSubscriptionUiState.Save) {
            observable.saveState(saveState)
        }

        override fun subscribe() {
            canGoBack = false
            observable.update(SubscriptionUiState.Loading)
            subscribeInner()
        }

        override fun subscribeInner() = handleAsync({
            subscribeInteractor.subscribe()
        }, uiBlock)

        override suspend fun subscribeInternal() = handleAsyncInternal({
            subscribeInteractor.subscribeInternal()
        }, uiBlock)

        override fun finish() {
            clear()
            clear.clear(SubscriptionRepresentative::class.java)
            navigation.update(DashboardScreen)
        }

        override fun startGettingUpdates(uiObserver: UiObserver<SubscriptionUiState>) {
            observable.updateObserver(uiObserver)
        }

        override fun stopGettingUpdates() {
            observable.updateObserver(EmptySubscriptionObserver)
        }

        override fun comeback() {
            if (canGoBack)
                finish()
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