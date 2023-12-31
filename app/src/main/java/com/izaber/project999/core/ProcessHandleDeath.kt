package com.izaber.project999.core

import android.util.Log

interface ProcessHandleDeath {
    fun firstOpening()
    fun wawDeathHappened(): Boolean
    fun deathHandled()

    class Base : ProcessHandleDeath {

        private var deathHappened = true

        private val tag: String
            get() = "ProcessDeathHandler"

        override fun firstOpening() {
            deathHappened = false
            Log.v(tag, "firstOpening()")
        }

        override fun wawDeathHappened(): Boolean {
            return deathHappened
        }

        override fun deathHandled() {
            deathHappened = false
        }
    }
}