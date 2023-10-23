package com.izaber.project999.dashboard

import com.izaber.project999.core.ClearRepresentative
import com.izaber.project999.core.Core
import com.izaber.project999.core.Module
import com.izaber.project999.main.UserPremiumCache

class DashboardModule(
    private val core: Core,
    private val clearRepresentative: ClearRepresentative
) : Module<DashboardRepresentative> {
    override fun representative(): DashboardRepresentative {
        val cache = UserPremiumCache.Base(core.sharedPreferences())

        return if (cache.isUserPremium())
            DashboardRepresentative.Premium(PremiumDashboardObservable.Base)
        else
            DashboardRepresentative.Base(core.navigation(), clearRepresentative)
    }
}