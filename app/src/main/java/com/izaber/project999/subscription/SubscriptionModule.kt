package com.izaber.project999.subscription

import com.izaber.project999.core.ClearRepresentative
import com.izaber.project999.core.Core
import com.izaber.project999.core.Module
import com.izaber.project999.core.ProcessDeathHandler
import com.izaber.project999.main.UserPremiumCache

class SubscriptionModule(
    private val core: Core,
    private val clear: ClearRepresentative
) : Module<SubscriptionRepresentative> {
    override fun representative(): SubscriptionRepresentative {
        return SubscriptionRepresentative.Base(
            ProcessDeathHandler.Base(),
            SubscriptionObservable.Base(),
            clear,
            UserPremiumCache.Base(core.sharedPreferences()),
            core.navigation()
        )
    }
}