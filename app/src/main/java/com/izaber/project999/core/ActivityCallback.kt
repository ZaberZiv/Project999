package com.izaber.project999.core

import com.izaber.project999.main.Screen

interface ActivityCallback : UiObserver<Screen> {
    override fun isEmpty(): Boolean = false
}