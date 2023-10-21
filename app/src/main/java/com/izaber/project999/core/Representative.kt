package com.izaber.project999.core

import android.util.Log

interface Representative<T : Any> {
    fun startGettingUpdates(uiObserver: UiObserver<T>) = Unit
    fun stopGettingUpdates() = Unit

//    private val deathHandler: ProcessDeathHandler = ProcessDeathHandler.Base()
//
//    fun activityCreated(firstOpening: Boolean) {
//        if (firstOpening) {
//            // init local cache
//            Log.v(tag, "Very first opening")
//            deathHandler.firstOpening()
//        } else {
//            if (deathHandler.wawDeathHappened()) {
//                // go to permanent storage and init localCache
//                Log.v(tag, "Process death happened")
//                deathHandler.deathHandled()
//            } else {
//                // use local cache and don't use permanent
//                Log.v(tag, "Activity recreated")
//            }
//        }
//    }

}