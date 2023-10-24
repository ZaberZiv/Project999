package com.izaber.project999.core

import com.izaber.project999.main.Screen

interface ActivityCallback : UiObserver<Screen>

object EmptyActivityCallback : ActivityCallback {
    override fun update(data: Screen) = Unit
}