package com.izaber.project999.dashboard

import com.izaber.project999.fakes.common.FakeClearRepresentative
import com.izaber.project999.fakes.common.FakeNavigation
import com.izaber.project999.subscription.screen.presentation.SubscriptionScreen
import org.junit.Before
import org.junit.Test

internal class BaseDashboardRepresentativeTest {

    private lateinit var dashboardRepresentative: DashboardRepresentative.Base
    private lateinit var navigation: FakeNavigation
    private lateinit var clearRepresentative: FakeClearRepresentative

    @Before
    fun setup() {
        navigation = FakeNavigation.Base()
        clearRepresentative = FakeClearRepresentative.Base()

        dashboardRepresentative = DashboardRepresentative.Base(
            navigation = navigation,
            clearRepresentative = clearRepresentative
        )
    }

    @Test
    fun `Dashboard representative Base main scenario`() {
        dashboardRepresentative.play()
        clearRepresentative.checkClearCalledWith(DashboardRepresentative::class.java)
        navigation.checkScreenUpdated(SubscriptionScreen)
    }
}
