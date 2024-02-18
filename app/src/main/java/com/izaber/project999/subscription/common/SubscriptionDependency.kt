package com.izaber.project999.subscription.common

import com.izaber.project999.core.ClearRepresentative
import com.izaber.project999.core.Core
import com.izaber.project999.core.Module
import com.izaber.project999.core.ProvideModule
import com.izaber.project999.core.Representative
import com.izaber.project999.subscription.progress.SubscriptionProgressModule
import com.izaber.project999.subscription.progress.presentation.SubscriptionProgressRepresentative
import com.izaber.project999.subscription.screen.SubscriptionModule
import com.izaber.project999.subscription.screen.presentation.SubscriptionRepresentative

class SubscriptionDependency(
    private val core: Core,
    private val clear: ClearRepresentative
) : ProvideModule {

    private var scopeModule: SubscriptionScopeModule? = null

    private val clearScopeModule: () -> Unit = {
        clear.clear(SubscriptionProgressRepresentative::class.java)
        clear.clear(SubscriptionRepresentative::class.java)
        scopeModule = null
    }

    private val provideScopeModule: () -> SubscriptionScopeModule = {
        if (scopeModule == null)
            scopeModule = SubscriptionScopeModule.Base()
        scopeModule!!
    }

    override fun <T : Representative<*>> module(clazz: Class<T>): Module<T> {
        return when (clazz) {
            SubscriptionRepresentative::class.java -> SubscriptionModule(
                core,
                clearScopeModule,
                provideScopeModule
            )

            SubscriptionProgressRepresentative::class.java -> SubscriptionProgressModule(
                core,
                provideScopeModule
            )

            else -> throw IllegalStateException("Unknown clazz $clazz")
        } as Module<T>
    }
}