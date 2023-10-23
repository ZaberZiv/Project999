package com.izaber.project999.core

import com.izaber.project999.dashboard.DashboardModule
import com.izaber.project999.dashboard.DashboardRepresentative
import com.izaber.project999.main.MainModule
import com.izaber.project999.main.MainRepresentative
import com.izaber.project999.subscription.SubscriptionModule
import com.izaber.project999.subscription.presentation.SubscriptionRepresentative

interface ProvideRepresentative {
    fun <T : Representative<*>> provideRepresentative(clazz: Class<T>): T

    class MakeDependency(
        private val core: Core,
        private val clear: ClearRepresentative
    ) : ProvideRepresentative {
        override fun <T : Representative<*>> provideRepresentative(clazz: Class<T>): T {
            return when (clazz) {
                MainRepresentative::class.java -> MainModule(core).representative()
                DashboardRepresentative::class.java -> DashboardModule(core).representative()
                SubscriptionRepresentative::class.java -> SubscriptionModule(
                    core,
                    clear
                ).representative()

                else -> throw IllegalArgumentException("unknown class $clazz")
            } as T
        }
    }

    class Factory(
        private val makeDependency: ProvideRepresentative
    ) : ProvideRepresentative, ClearRepresentative {

        private val representativeMap =
            mutableMapOf<Class<out Representative<*>>, Representative<*>>()

        override fun clear(clazz: Class<out Representative<*>>) {
            representativeMap.remove(clazz)
        }

        override fun <T : Representative<*>> provideRepresentative(clazz: Class<T>): T {
            return if (representativeMap.containsKey(clazz)) {
                representativeMap[clazz] as T
            } else {
                val representative = makeDependency.provideRepresentative(clazz)
                representativeMap[clazz] = representative
                representative
            }
        }
    }
}