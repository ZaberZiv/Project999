package com.izaber.project999.dashboard

import android.util.Log
import com.izaber.project999.core.Module
import com.izaber.project999.core.Core
import com.izaber.project999.main.UserPremiumCache

class DashboardModule(
    private val core: Core
) : Module<DashboardRepresentative> {
    override fun representative(): DashboardRepresentative {
        val cache = UserPremiumCache.Base(core.sharedPreferences())

        Log.e("Ilya", "DashboardModule: cache -> ${cache.isUserPremium()}")
        return if (cache.isUserPremium())
            DashboardRepresentative.Premium(PremiumDashboardObservable.Base)
        else
            DashboardRepresentative.Base(core.navigation())
    }
}