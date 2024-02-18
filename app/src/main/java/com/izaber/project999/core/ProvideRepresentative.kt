package com.izaber.project999.core

import com.izaber.project999.dashboard.DashboardModule
import com.izaber.project999.dashboard.DashboardRepresentative
import com.izaber.project999.main.MainModule
import com.izaber.project999.main.MainRepresentative
import com.izaber.project999.subscription.common.SubscriptionDependency

interface ProvideRepresentative {
    fun <T : Representative<*>> provideRepresentative(clazz: Class<T>): T

    class MakeDependency(
        private val core: Core,
        private val clear: ClearRepresentative,
        private val provideModule: ProvideModule
    ) : ProvideModule {
        override fun <T : Representative<*>> module(clazz: Class<T>): Module<T> {
            return when (clazz) {
                MainRepresentative::class.java -> MainModule(core)
                DashboardRepresentative::class.java -> DashboardModule(core, clear)
                else -> provideModule.module(clazz)
            } as Module<T>
        }
    }

    class Factory(
        core: Core,
        clear: ClearRepresentative,
    ) : ProvideRepresentative, ClearRepresentative {

        private val makeDependency: ProvideModule = MakeDependency(
            core = core,
            clear = clear,
            provideModule = SubscriptionDependency(core, clear)
        )

        private val representativeMap =
            mutableMapOf<Class<out Representative<*>>, Representative<*>>()

        override fun <T : Representative<*>> provideRepresentative(clazz: Class<T>): T {
            return if (representativeMap.containsKey(clazz)) {
                representativeMap[clazz] as T
            } else {
                val representative =
                    makeDependency.module(clazz).representative()
                representativeMap[clazz] = representative
                representative
            }
        }

        override fun clear(clazz: Class<out Representative<*>>) {
            representativeMap.remove(clazz)
        }
    }
}

interface ProvideModule {
    fun <T : Representative<*>> module(clazz: Class<T>): Module<T>
}