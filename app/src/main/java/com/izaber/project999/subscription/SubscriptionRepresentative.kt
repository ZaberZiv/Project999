package com.izaber.project999.subscription

import android.util.Log
import com.izaber.project999.core.ClearRepresentative
import com.izaber.project999.core.Representative
import com.izaber.project999.dashboard.DashboardRepresentative
import com.izaber.project999.dashboard.DashboardScreen
import com.izaber.project999.main.Navigation
import com.izaber.project999.main.UserPremiumCache

interface SubscriptionRepresentative : Representative<Unit> {

    fun subscribe()

    class Base(
        private val clear: ClearRepresentative,
        private val userPremiumCache: UserPremiumCache.Save,
        private val navigation: Navigation.Update
    ) : SubscriptionRepresentative {

        init {
            Log.e("Ilya", "init SubscriptionRepresentative")
        }

        override fun subscribe() {
            userPremiumCache.saveUserPremium()
            clear.clear(DashboardRepresentative::class.java)
            clear.clear(SubscriptionRepresentative::class.java)
            navigation.update(DashboardScreen)
        }
    }
}