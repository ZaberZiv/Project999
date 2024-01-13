package com.izaber.project999.core

import android.content.Context
import android.content.SharedPreferences
import androidx.work.WorkManager
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

interface ProvideWorkManager {
    fun workManager(): WorkManager
}

interface Core : ProvideNavigation, ProvideSharedPreferences, ProvideRunAsync, ProvideWorkManager {
    class Base(
        private val context: Context
    ) : Core {

        private val navigation = Navigation.Base()

        private val runAsync by lazy { RunAsync.Base(DispatchersList.Base()) }

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

        override fun workManager(): WorkManager {
            return WorkManager.getInstance(context)
        }
    }
}