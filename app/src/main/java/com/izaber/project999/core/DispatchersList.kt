package com.izaber.project999.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher

interface DispatchersList {

    fun io(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher
    fun def(): CoroutineDispatcher
    fun unc(): CoroutineDispatcher

    class Base : DispatchersList {
        override fun io() = Dispatchers.IO
        override fun ui() = Dispatchers.Main
        override fun def() = Dispatchers.Default
        override fun unc() = Dispatchers.Unconfined
    }

    class Test(
        private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    ) : DispatchersList {
        override fun io() = testDispatcher
        override fun ui() = testDispatcher
        override fun def() = testDispatcher
        override fun unc() = testDispatcher
    }
}