package com.izaber.project999.subscription.progress

import com.izaber.project999.core.Core
import com.izaber.project999.core.HandleDeath
import com.izaber.project999.core.Module
import com.izaber.project999.main.UserPremiumCache
import com.izaber.project999.subscription.common.SubscriptionScopeModule
import com.izaber.project999.subscription.progress.data.BaseSubscriptionRepository
import com.izaber.project999.subscription.progress.data.ForegroundServiceWrapper
import com.izaber.project999.subscription.progress.data.SubscriptionCloudDataSource
import com.izaber.project999.subscription.progress.domain.SubscriptionInteractor
import com.izaber.project999.subscription.progress.presentation.SubscriptionProgressObservable
import com.izaber.project999.subscription.progress.presentation.SubscriptionProgressRepresentative
import com.izaber.project999.subscription.screen.presentation.SubscriptionUiMapper

class SubscriptionProgressModule(
    private val core: Core,
    private val provideScopeModule: () -> SubscriptionScopeModule
) : Module<SubscriptionProgressRepresentative> {
    override fun representative(): SubscriptionProgressRepresentative {
        val progressObservable = SubscriptionProgressObservable.Base()
        return SubscriptionProgressRepresentative.Base(
            runAsync = core.runAsync(),
            progressObservable = progressObservable,
            handleDeath = HandleDeath.Base(),
            subscribeInteractor = SubscriptionInteractor.Base(
                BaseSubscriptionRepository(
                    foregroundServiceWrapper = ForegroundServiceWrapper.Base(core.workManager()),
                    cloudDataSource = SubscriptionCloudDataSource.Base(),
                    userPremiumCache = UserPremiumCache.Base(
                        core.sharedPreferences()
                    )
                )
            ),
            mapper = SubscriptionUiMapper(
                observable = provideScopeModule.invoke().provideSubscriptionObservable(),
                progressObservable = progressObservable
            )
        )
    }
}