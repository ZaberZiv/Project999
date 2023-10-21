package com.izaber.project999.core

interface Module<T: Representative<*>> {
    fun representative(): T
}