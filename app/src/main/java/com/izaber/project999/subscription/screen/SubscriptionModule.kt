package com.izaber.project999.subscription.screen

import com.izaber.project999.core.Core
import com.izaber.project999.core.HandleDeath
import com.izaber.project999.core.Module
import com.izaber.project999.subscription.common.SubscriptionScopeModule
import com.izaber.project999.subscription.screen.presentation.SubscriptionRepresentative

class SubscriptionModule(
    private val core: Core,
    private val clear: () -> Unit,
    private val provideScopeModule: () -> SubscriptionScopeModule
) : Module<SubscriptionRepresentative> {
    override fun representative(): SubscriptionRepresentative {
        return SubscriptionRepresentative.Base(
            handleDeath = HandleDeath.Base(),
            observable = provideScopeModule.invoke().provideSubscriptionObservable(),
            clear = clear,
            navigation = core.navigation()
        )
    }
}