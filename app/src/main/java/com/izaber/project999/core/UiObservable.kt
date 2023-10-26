package com.izaber.project999.core

interface UiObservable<T : Any> : UiUpdate<T>, UpdateObserver<T> {
    fun clear()

    abstract class Base<T : Any>(
        private val empty: T
    ) : UiObservable<T> {

        protected var cache: T = empty

        private var observer: UiObserver<T> = UiObserver.Empty()

        override fun clear() {
            cache = empty
        }

        override fun updateObserver(uiObserver: UiObserver<T>) {
            observer = uiObserver
            observer.update(cache)
        }

        override fun update(data: T) {
            cache = data
            observer.update(data)
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