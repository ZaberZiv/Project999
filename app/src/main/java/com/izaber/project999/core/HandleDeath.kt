package com.izaber.project999.core

import android.util.Log

interface HandleDeath {
    fun firstOpening()
    fun didDeathHappened(): Boolean
    fun deathHandled()

    class Base : HandleDeath {

        private var deathHappened = true

        private val tag: String
            get() = "ProcessDeathHandler"

        override fun firstOpening() {
            deathHappened = false
            Log.v(tag, "firstOpening()")
        }

        override fun didDeathHappened(): Boolean {
            return deathHappened
        }

        override fun deathHandled() {
            deathHappened = false
        }
    }
}