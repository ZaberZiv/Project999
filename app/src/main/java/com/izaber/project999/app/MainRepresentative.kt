package com.izaber.project999.app

import com.izaber.project999.R

interface MainRepresentative {
    fun startGettingUpdates(uiObserver: UiObserver<Int>)
    fun stopGettingUpdates()
    fun startAsync()

    class Base(
        private val observable: UiObservable<Int>
    ) : MainRepresentative {

        private val thread = Thread {
            Thread.sleep(5000)
            observable.update(R.string.hello)
        }

        override fun startGettingUpdates(uiObserver: UiObserver<Int>) {
            observable.updateObserver(uiObserver)
        }

        override fun stopGettingUpdates() {
            observable.updateObserver()
        }

        override fun startAsync() {
            thread.start()
        }
    }
}