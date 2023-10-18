package com.izaber.project999.app

import android.app.Application
import android.util.Log

class App: Application() {

    private val tag = "APP"
    private val deathHandler: ProcessDeathHandler = ProcessDeathHandler.Base()
    val mainRepresentative: MainRepresentative = MainRepresentative.Base(UiObservable.Single())

    override fun onCreate() {
        super.onCreate()
        Log.v(tag, "App created")
    }

    fun activityCreated(firstOpening: Boolean) {
        if (firstOpening) {
            // init local cache
            Log.v(tag, "Very first opening")
            deathHandler.firstOpening()
        } else {
            if (deathHandler.wawDeathHappened()) {
                // go to permanent storage and init localCache
                Log.v(tag, "Process death happened")
                deathHandler.deathHandled()
            } else {
                // use local cache and don't use permanent
                Log.v(tag, "Activity recreated")
            }
        }
    }
}