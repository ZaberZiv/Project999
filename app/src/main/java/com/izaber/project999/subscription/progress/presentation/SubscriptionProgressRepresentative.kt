package com.izaber.project999.subscription.progress.presentation

import com.izaber.project999.core.HandleDeath
import com.izaber.project999.core.Representative
import com.izaber.project999.core.RunAsync
import com.izaber.project999.core.UiObserver
import com.izaber.project999.subscription.progress.domain.SubscriptionInteractor
import com.izaber.project999.subscription.progress.domain.SubscriptionResult
import com.izaber.project999.subscription.screen.presentation.SubscriptionInner

interface SubscriptionProgressRepresentative : Representative<SubscriptionProgressUiState>,
    SubscriptionInner, ComeBack<ComeBack<Boolean>>, Init, Observed, Subscribe {
    suspend fun subscribeInternal()
    fun save(saveState: SaveAndRestoreSubscriptionUiState.Save)
    fun restore(restoreState: SaveAndRestoreSubscriptionUiState.Restore)

    class Base(
        runAsync: RunAsync,
        private val progressObservable: SubscriptionProgressObservable,
        private val handleDeath: HandleDeath,
        private val subscribeInteractor: SubscriptionInteractor,
        private val mapper: SubscriptionResult.Mapper
    ) : Representative.Abstract<SubscriptionProgressUiState>(runAsync),
        SubscriptionProgressRepresentative {

        private val uiBlock: (SubscriptionResult) -> Unit = { result ->
            result.map(mapper)
        }

        override fun init(firstRun: Boolean) {
            if (firstRun) {
                handleDeath.firstOpening()
                progressObservable.update(SubscriptionProgressUiState.Hide)
            }
        }

        override fun restore(restoreState: SaveAndRestoreSubscriptionUiState.Restore) {
            if (handleDeath.didDeathHappened()) {
                handleDeath.deathHandled()
                val uiState = restoreState.restore()
                uiState.restoreAfterDeath(this, progressObservable)
            }
        }

        override fun startGettingUpdates(uiObserver: UiObserver<SubscriptionProgressUiState>) {
            progressObservable.updateObserver(uiObserver)
        }

        override fun observed() {
            progressObservable.clear()
        }

        override fun stopGettingUpdates() {
            progressObservable.updateObserver()
        }

        override fun save(saveState: SaveAndRestoreSubscriptionUiState.Save) {
            progressObservable.save(saveState)
        }

        override fun comeback(data: ComeBack<Boolean>) {
            data.comeback(progressObservable.canGoBack())
        }

        override fun subscribe() {
            progressObservable.update(SubscriptionProgressUiState.Show)
            subscribeInner()
        }

        override fun subscribeInner() {
            handleAsync({
                subscribeInteractor.subscribe()
            }, uiBlock)
        }

        override suspend fun subscribeInternal() = handleAsyncInternal({
            subscribeInteractor.subscribeInternal()
        }, uiBlock)
    }
}