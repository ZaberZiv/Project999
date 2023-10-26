package com.izaber.project999.core

import android.content.Context
import android.content.SharedPreferences
import com.izaber.project999.R
import com.izaber.project999.main.Navigation

interface ProvideSharedPreferences {
    fun sharedPreferences(): SharedPreferences
}

interface ProvideNavigation {
    fun navigation(): Navigation.Mutable
}

interface ProvideRunAsync {
    fun runAsync(): RunAsync
}

interface Core : ProvideNavigation, ProvideSharedPreferences, ProvideRunAsync {
    class Base(
        private val context: Context
    ) : Core {

        private val navigation = Navigation.Base()
        private val runAsync = RunAsync.Base(DispatchersList.Base())

        override fun navigation(): Navigation.Mutable = navigation

        override fun sharedPreferences(): SharedPreferences {
            return context.getSharedPreferences(
                context.getString(R.string.app_name),
                Context.MODE_PRIVATE
            )
        }

        override fun runAsync(): RunAsync {
            return runAsync
        }
    }
}