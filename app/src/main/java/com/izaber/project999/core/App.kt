package com.izaber.project999.core

import android.app.Application
import android.util.Log

class App : Application(), ProvideRepresentative, ClearRepresentative {

    val tag = "Ilya"

    private val representativeMap = mutableMapOf<Class<out Representative<*>>, Representative<*>>()
    private lateinit var core: Core
    private lateinit var factory: ProvideRepresentative.Factory

    override fun onCreate() {
        super.onCreate()
        Log.v(tag, "App created")
        core = Core.Base(this)
        factory = ProvideRepresentative.Factory(core, this)
    }

    override fun clear(clazz: Class<out Representative<*>>) {
        representativeMap.remove(clazz)
    }

    override fun <T : Representative<*>> provideRepresentative(clazz: Class<T>): T {
        return if (representativeMap.containsKey(clazz)) {
            representativeMap[clazz] as T
        } else {
            val representative = factory.provideRepresentative(clazz)
            representativeMap[clazz] = representative
            representative
        }
    }
}