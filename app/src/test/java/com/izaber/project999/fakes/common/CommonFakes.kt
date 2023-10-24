package com.izaber.project999.fakes.common

import com.izaber.project999.core.ActivityCallback
import com.izaber.project999.core.ClearRepresentative
import com.izaber.project999.core.Representative
import com.izaber.project999.core.UiObserver
import com.izaber.project999.main.Navigation
import com.izaber.project999.main.Screen
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals

internal interface FakeClearRepresentative : ClearRepresentative {

    fun checkClearCalledWith(clasz: Class<out Representative<*>>)

    class Base : FakeClearRepresentative {

        private var clearCallerClasz: Class<out Representative<*>>? = null

        override fun checkClearCalledWith(clasz: Class<out Representative<*>>) {
            TestCase.assertEquals(clasz, clearCallerClasz)
        }

        override fun clear(clazz: Class<out Representative<*>>) {
            clearCallerClasz = clazz
        }
    }
}

internal interface FakeNavigation : Navigation.Update {
    fun checkScreenUpdated(screen: Screen)

    class Base : FakeNavigation {

        private var callbackScreen: Screen = Screen.Empty

        override fun update(data: Screen) {
            callbackScreen = data
        }

        override fun checkScreenUpdated(screen: Screen) {
            TestCase.assertEquals(screen, callbackScreen)
        }
    }
}

internal interface FakeMutableNavigation : Navigation.Mutable {

    fun checkScreenUpdated(screen: Screen)
    fun checkClearCalled()
    fun checkUpdateObserverCalled(observer: ActivityCallback)

    class Base : FakeMutableNavigation {
        private var isClearCalled = false
        private var callbackScreen: Screen = Screen.Empty
        private var callback: UiObserver<Screen> = object : ActivityCallback {
            override fun update(data: Screen) = Unit
        }

        override fun update(data: Screen) {
            callbackScreen = data
        }

        override fun checkScreenUpdated(screen: Screen) {
            assertEquals(screen, callbackScreen)
        }

        override fun updateObserver(uiObserver: UiObserver<Screen>) {
            callback = uiObserver
        }

        override fun checkUpdateObserverCalled(observer: ActivityCallback) {
            assertEquals(observer, callback)
        }

        override fun clear() {
            isClearCalled = true
        }

        override fun checkClearCalled() {
            assertEquals(true, isClearCalled)
            isClearCalled = false
        }
    }
}