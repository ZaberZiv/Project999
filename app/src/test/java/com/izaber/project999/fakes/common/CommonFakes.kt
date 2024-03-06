package com.izaber.project999.fakes.common

import com.izaber.project999.core.ActivityCallback
import com.izaber.project999.core.ClearRepresentative
import com.izaber.project999.core.Representative
import com.izaber.project999.core.RunAsync
import com.izaber.project999.core.UiObserver
import com.izaber.project999.fakes.FakeObservable
import com.izaber.project999.main.Navigation
import com.izaber.project999.main.Screen
import com.izaber.project999.subscription.progress.domain.SubscriptionResult
import com.izaber.project999.subscription.screen.presentation.SubscriptionUiState
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

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

interface FakeRunAsync : RunAsync {
    fun checkClearCalledTimes(times: Int)
    fun pingResult()
    class Base : FakeRunAsync {

        private var cachedBlock: (Any) -> Unit = {}

        private var cached: Any = Unit

        private var clearCalledTimes = 0

        override fun <T : Any> runAsync(
            scope: CoroutineScope,
            backgroundBlock: suspend () -> T,
            uiBlock: (T) -> Unit
        ) = runBlocking {
            cached = backgroundBlock()
            cachedBlock = uiBlock as (Any) -> Unit
        }

        override suspend fun <T : Any> runAsync(
            backgroundBlock: suspend () -> T,
            uiBlock: (T) -> Unit
        ) {
            uiBlock.invoke(backgroundBlock.invoke())
        }

        override fun pingResult() {
            cachedBlock(cached)
        }

        override fun checkClearCalledTimes(times: Int) {
            assertEquals(times, clearCalledTimes)
        }

        override fun clear() {
            clearCalledTimes++
        }
    }
}

internal class FakeMapper(
    private val observable: FakeObservable
) : SubscriptionResult.Mapper {
    override fun mapSuccess() {
        observable.update(SubscriptionUiState.Success)
    }
}