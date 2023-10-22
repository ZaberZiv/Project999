package com.izaber.project999.core

import android.app.Application
import android.util.Log
import com.izaber.project999.utils.TAG

class App : Application(), ProvideRepresentative, ClearRepresentative {

    private val representativeMap = mutableMapOf<Class<out Representative<*>>, Representative<*>>()
    private lateinit var core: Core
    private lateinit var factory: ProvideRepresentative.Factory

    override fun onCreate() {
        super.onCreate()
        Log.v(TAG, "App created")
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