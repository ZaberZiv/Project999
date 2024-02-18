package com.izaber.project999.fakes

import com.izaber.project999.core.HandleDeath
import com.izaber.project999.core.UiObserver
import com.izaber.project999.subscription.progress.domain.SubscriptionInteractor
import com.izaber.project999.subscription.progress.domain.SubscriptionResult
import com.izaber.project999.subscription.screen.presentation.SaveAndRestoreSubscriptionUiState
import com.izaber.project999.subscription.screen.presentation.SubscriptionCallBack
import com.izaber.project999.subscription.screen.presentation.SubscriptionObservable
import com.izaber.project999.subscription.screen.presentation.SubscriptionUiState
import junit.framework.TestCase.assertEquals


internal interface FakeSaveAndRestore : SaveAndRestoreSubscriptionUiState.Mutable {

    class Base : FakeSaveAndRestore {

        private var state: SubscriptionUiState? = null

        override fun isEmpty(): Boolean {
            return state == null
        }

        override fun save(data: SubscriptionUiState) {
            state = data
        }

        override fun restore(): SubscriptionUiState {
            return state!!
        }
    }
}

internal interface FakeSubscriptionInteractor : SubscriptionInteractor {

    fun checkSubscribeCalledTimes(times: Int)

    class Base : FakeSubscriptionInteractor {
        private var subscribedTimesCount = 0

        override suspend fun subscribe(): SubscriptionResult {
            subscribedTimesCount++
            return SubscriptionResult.NoDataYet
        }

        override suspend fun subscribeInternal(): SubscriptionResult {
            return SubscriptionResult.Success
        }

        override fun checkSubscribeCalledTimes(times: Int) {
            assertEquals(times, subscribedTimesCount)
        }
    }
}

internal interface FakeObservable : SubscriptionObservable {
    fun checkClearCalled()
    fun checkUiState(uiState: SubscriptionUiState)
    fun checkUpdateObserverCalled(observer: SubscriptionCallBack)
    fun checkUpdateCalledCount(times: Int)

    class Base : FakeObservable {

        private var clearCalled = false
        private var cache: SubscriptionUiState = SubscriptionUiState.Empty
        private var observer: UiObserver<SubscriptionUiState> = object : SubscriptionCallBack {
            override fun update(data: SubscriptionUiState) = Unit
        }
        private var updateCalledCount = 0

        override fun clear() {
            clearCalled = true
            cache = SubscriptionUiState.Empty
        }

        override fun checkClearCalled() {
            assertEquals(true, clearCalled)
            clearCalled = false
        }

        override fun update(data: SubscriptionUiState) {
            cache = data
            updateCalledCount++
        }

        override fun checkUpdateCalledCount(times: Int) {
            assertEquals(times, updateCalledCount)
        }

        override fun updateObserver(uiObserver: UiObserver<SubscriptionUiState>) {
            observer = uiObserver
        }

        override fun checkUpdateObserverCalled(observer: SubscriptionCallBack) {
            assertEquals(observer, this.observer)
        }

        override fun saveState(saveState: SaveAndRestoreSubscriptionUiState.Save) {
            saveState.save(cache)
        }

        override fun checkUiState(uiState: SubscriptionUiState) {
            assertEquals(uiState, cache)
        }
    }
}

internal interface FakeHandleDeath : HandleDeath {

    fun checkFirstOpeningCalled(times: Int)

    class Base : FakeHandleDeath {

        private var deathHappened: Boolean = true
        private var firstOpeningCalledTimes = 0

        override fun checkFirstOpeningCalled(times: Int) {
            assertEquals(times, firstOpeningCalledTimes)
        }

        override fun firstOpening() {
            deathHappened = false
            firstOpeningCalledTimes++
        }

        override fun didDeathHappened(): Boolean {
            return deathHappened
        }

        override fun deathHandled() {
            deathHappened = false
        }
    }
}
