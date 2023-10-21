package com.izaber.project999.core

import com.izaber.project999.dashboard.DashboardModule
import com.izaber.project999.dashboard.DashboardRepresentative
import com.izaber.project999.main.MainModule
import com.izaber.project999.main.MainRepresentative
import com.izaber.project999.subscription.SubscriptionModule
import com.izaber.project999.subscription.SubscriptionRepresentative

interface ProvideRepresentative {
    fun <T: Representative<*>> provideRepresentative(clazz: Class<T>): T

    class Factory(
        private val core : Core,
        private val clear: ClearRepresentative
    ): ProvideRepresentative {
        override fun <T : Representative<*>> provideRepresentative(clazz: Class<T>): T {
            return when (clazz) {
                MainRepresentative::class.java -> MainModule(core).representative()
                DashboardRepresentative::class.java -> DashboardModule(core).representative()
                SubscriptionRepresentative::class.java -> SubscriptionModule(core, clear).representative()
                else -> throw IllegalArgumentException("unknown class $clazz")
            } as T
        }
    }
}