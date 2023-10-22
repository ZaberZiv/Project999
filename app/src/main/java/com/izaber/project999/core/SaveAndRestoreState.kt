package com.izaber.project999.core

import android.os.Build
import android.os.Bundle
import java.io.Serializable

interface SaveAndRestoreState {

    interface Save<T : Serializable> {
        fun save(data: T)
    }

    interface Restore<T : Serializable> : IsEmpty {
        fun restore(): T
    }

    interface Mutable<T : Serializable> : Save<T>, Restore<T>

    abstract class Abstract<T : Serializable>(
        private val bundle: Bundle?,
        private val key: String,
        private val clasz: Class<T>
    ) : Mutable<T> {
        override fun isEmpty(): Boolean = bundle == null

        override fun save(data: T) {
            bundle!!.putSerializable(key, data)
        }

        override fun restore(): T {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle!!.getSerializable(key, clasz)!!
            } else {
                bundle!!.getSerializable(key)!! as T
            }
        }
    }
}