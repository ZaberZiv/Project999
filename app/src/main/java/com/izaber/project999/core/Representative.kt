package com.izaber.project999.core

interface Representative<T : Any> {
    fun startGettingUpdates(uiObserver: UiObserver<T>) = Unit
    fun stopGettingUpdates() = Unit
}