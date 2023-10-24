package com.izaber.project999.fakes

import com.izaber.project999.core.UiObserver
import com.izaber.project999.dashboard.DashboardCallBack
import com.izaber.project999.dashboard.PremiumDashboardObservable
import com.izaber.project999.dashboard.PremiumDashboardUiState
import junit.framework.TestCase

internal interface FakePremiumDashboardObservable : PremiumDashboardObservable {

    fun checkClearCalled()
    fun checkUiState(uiState: PremiumDashboardUiState)
    fun checkUpdateObserverCalled(observer: DashboardCallBack)

    class Base : FakePremiumDashboardObservable {

        private var clearCalled = false
        private var cache: PremiumDashboardUiState = PremiumDashboardUiState.Empty
        private var observer: UiObserver<PremiumDashboardUiState> = object : DashboardCallBack {
            override fun update(data: PremiumDashboardUiState) = Unit
        }

        override fun clear() {
            clearCalled = true
            cache = PremiumDashboardUiState.Empty
        }

        override fun checkClearCalled() {
            TestCase.assertEquals(true, clearCalled)
            clearCalled = false
        }

        override fun update(data: PremiumDashboardUiState) {
            cache = data
        }

        override fun checkUiState(uiState: PremiumDashboardUiState) {
            TestCase.assertEquals(uiState, cache)
        }

        override fun updateObserver(uiObserver: UiObserver<PremiumDashboardUiState>) {
            observer = uiObserver
        }

        override fun checkUpdateObserverCalled(observer: DashboardCallBack) {
            TestCase.assertEquals(observer, this.observer)
        }
    }
}