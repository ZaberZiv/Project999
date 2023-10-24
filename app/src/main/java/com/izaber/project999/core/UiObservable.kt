package com.izaber.project999.core

import androidx.annotation.MainThread

interface UiObservable<T : Any> : UiUpdate<T>, UpdateObserver<T> {
    fun clear()

    abstract class Single<T : Any>(
        private val empty: T
    ) : UiObservable<T> {

        @Volatile
        protected var cache: T = empty

        @Volatile
        private var observer: UiObserver<T> = UiObserver.Empty()

        override fun clear() {
            cache = empty
        }

        @MainThread
        override fun updateObserver(uiObserver: UiObserver<T>) = synchronized(lock) {
            observer = uiObserver
            observer.update(cache)
        }

        override fun update(data: T) = synchronized(lock) {
            cache = data
            observer.update(data)
        }

        companion object {
            private val lock = Object()
        }
    }
}

interface UiObserver<T : Any> : UiUpdate<T> {
    class Empty<T : Any> : UiObserver<T> {
        override fun update(data: T) = Unit
    }
}

interface UiUpdate<T : Any> {
    fun update(data: T)
}

interface UpdateObserver<T : Any> {
    fun updateObserver(uiObserver: UiObserver<T> = UiObserver.Empty())
}