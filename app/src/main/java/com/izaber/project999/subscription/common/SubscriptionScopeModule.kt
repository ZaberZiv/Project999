package com.izaber.project999.subscription.common

import com.izaber.project999.subscription.screen.presentation.SubscriptionObservable

interface SubscriptionScopeModule {
    fun provideSubscriptionObservable(): SubscriptionObservable

    class Base : SubscriptionScopeModule {
        private val observable = SubscriptionObservable.Base()
        override fun provideSubscriptionObservable(): SubscriptionObservable {
            return observable
        }
    }
}