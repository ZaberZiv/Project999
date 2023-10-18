package com.izaber.project999.app

import androidx.annotation.MainThread

interface UiObservable<T : Any> {
    fun update(data: T)
    fun updateObserver(uiObserver: UiObserver<T> = UiObserver.Empty())

    class Base<T : Any> : UiObservable<T> {
        override fun update(data: T) = Unit
        override fun updateObserver(uiObserver: UiObserver<T>) = Unit
    }

    class Single<T : Any> : UiObservable<T> {

        @Volatile
        private var cache: T? = null

        @Volatile
        private var observer: UiObserver<T> = UiObserver.Empty()

        @MainThread
        override fun updateObserver(uiObserver: UiObserver<T>) = synchronized(lock) {
            observer = uiObserver
            if (observer.isEmpty().not()) {
                cache?.let {
                    observer.update(it)
                }
            }
        }

        override fun update(data: T) = synchronized(lock) {
            if (observer.isEmpty()) {
                cache = data
            } else {
                cache = data
                observer.update(data)
            }
        }

        companion object {
            private val lock = Object()
        }
    }
}

interface UiObserver<T : Any> : UiUpdate<T> {
    fun isEmpty(): Boolean = false

    class Empty<T : Any> : UiObserver<T> {
        override fun isEmpty() = true
        override fun update(data: T) = Unit
    }
}

interface UiUpdate<T : Any> {
    fun update(data: T)
}