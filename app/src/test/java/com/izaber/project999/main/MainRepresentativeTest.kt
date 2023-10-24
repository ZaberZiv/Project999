package com.izaber.project999.main

import com.izaber.project999.core.ActivityCallback
import com.izaber.project999.core.EmptyActivityCallback
import com.izaber.project999.dashboard.DashboardScreen
import com.izaber.project999.fakes.common.FakeMutableNavigation
import org.junit.Before
import org.junit.Test

internal class MainRepresentativeTest {

    private lateinit var mainRepresentative: MainRepresentative
    private lateinit var navigation: FakeMutableNavigation

    @Before
    fun setup() {
        navigation = FakeMutableNavigation.Base()

        mainRepresentative = MainRepresentative.Base(
            navigation = navigation
        )
    }

    @Test
    fun `Main representative Base main scenario`() {
        val callback = object : ActivityCallback {
            override fun update(data: Screen) = Unit
        }

        mainRepresentative.startGettingUpdates(callback)
        navigation.checkUpdateObserverCalled(callback)

        mainRepresentative.showDashboard(true)
        navigation.checkScreenUpdated(DashboardScreen)

        mainRepresentative.observed()
        navigation.checkClearCalled()

        mainRepresentative.stopGettingUpdates()
        navigation.checkUpdateObserverCalled(EmptyActivityCallback)

        mainRepresentative.showDashboard(false)
        navigation.checkScreenUpdated(DashboardScreen)

        mainRepresentative.startGettingUpdates(callback)
        navigation.checkUpdateObserverCalled(callback)
    }
}