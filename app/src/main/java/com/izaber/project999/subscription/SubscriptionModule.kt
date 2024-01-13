package com.izaber.project999.subscription

import com.izaber.project999.core.ClearRepresentative
import com.izaber.project999.core.Core
import com.izaber.project999.core.Module
import com.izaber.project999.core.ProcessHandleDeath
import com.izaber.project999.main.UserPremiumCache
import com.izaber.project999.subscription.data.BaseSubscriptionRepository
import com.izaber.project999.subscription.data.ForegroundServiceWrapper
import com.izaber.project999.subscription.data.SubscriptionCloudDataSource
import com.izaber.project999.subscription.domain.SubscriptionInteractor
import com.izaber.project999.subscription.presentation.SubscriptionObservable
import com.izaber.project999.subscription.presentation.SubscriptionRepresentative
import com.izaber.project999.subscription.presentation.SubscriptionUiMapper

class SubscriptionModule(
    private val core: Core,
    private val clear: ClearRepresentative
) : Module<SubscriptionRepresentative> {
    override fun representative(): SubscriptionRepresentative {
        val observable = SubscriptionObservable.Base()
        return SubscriptionRepresentative.Base(
            runAsync = core.runAsync(),
            handleDeath = ProcessHandleDeath.Base(),
            observable = observable,
            clear = clear,
            subscribeInteractor = SubscriptionInteractor.Base(
                BaseSubscriptionRepository(
                    ForegroundServiceWrapper.Base(
                        workManager = core.workManager()
                    ),
                    SubscriptionCloudDataSource.Base(),
                    UserPremiumCache.Base(core.sharedPreferences())
                )
            ),
            navigation = core.navigation(),
            mapper = SubscriptionUiMapper(observable)
        )
    }
}