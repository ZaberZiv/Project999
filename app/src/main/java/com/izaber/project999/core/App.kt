package com.izaber.project999.core

import android.app.Application
import android.util.Log
import com.izaber.project999.utils.TAG

class App : Application(), ProvideRepresentative, ClearRepresentative {

    private lateinit var factory: ProvideRepresentative.Factory

    override fun onCreate() {
        super.onCreate()
        Log.v(TAG, "App created")
        factory = ProvideRepresentative.Factory(
            ProvideRepresentative.MakeDependency(
                core = Core.Base(this),
                clear = this
            )
        )
    }

    override fun clear(clazz: Class<out Representative<*>>) {
        factory.clear(clazz)
    }

    override fun <T : Representative<*>> provideRepresentative(clazz: Class<T>): T {
        return factory.provideRepresentative(clazz)
    }
}