package com.izaber.project999.dashboard

import com.izaber.project999.fakes.FakePremiumDashboardObservable
import org.junit.Before
import org.junit.Test


internal class PremiumDashboardRepresentativeTest {

    private lateinit var dashboardRepresentative: DashboardRepresentative.Premium
    private lateinit var observable: FakePremiumDashboardObservable

    @Before
    fun setup() {
        observable = FakePremiumDashboardObservable.Base()

        dashboardRepresentative = DashboardRepresentative.Premium(
            observable = observable
        )
    }

    @Test
    fun `Dashboard representative Premium main scenario`() {
        observable.checkUiState(PremiumDashboardUiState.Empty)

        val callBack = object : DashboardCallBack {
            override fun update(data: PremiumDashboardUiState) = Unit
        }

        dashboardRepresentative.startGettingUpdates(callBack)
        observable.checkUpdateObserverCalled(callBack)

        dashboardRepresentative.play()
        observable.checkUiState(PremiumDashboardUiState.Playing)

        dashboardRepresentative.observed()
        observable.checkClearCalled()
        observable.checkUiState(PremiumDashboardUiState.Empty)

        dashboardRepresentative.stopGettingUpdates()
        observable.checkUpdateObserverCalled(EmptyDashboardObserver)
    }
}