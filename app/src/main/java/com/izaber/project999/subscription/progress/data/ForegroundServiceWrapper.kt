package com.izaber.project999.subscription.progress.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.izaber.project999.core.ProvideRepresentative
import com.izaber.project999.subscription.progress.presentation.SubscriptionProgressRepresentative

interface ForegroundServiceWrapper {
    fun start()

    class Base(
        private val workManager: WorkManager
    ) : ForegroundServiceWrapper {
        override fun start() {
            val work = OneTimeWorkRequestBuilder<Worker>().build()
            workManager.beginUniqueWork(
                WORK_NAME,
                ExistingWorkPolicy.KEEP,
                work
            ).enqueue()
        }
    }
}

private const val WORK_NAME = "load async"

class Worker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val representative = (applicationContext as ProvideRepresentative).provideRepresentative(
            SubscriptionProgressRepresentative::class.java
        )
        representative.subscribeInternal()
        return Result.success()
    }
}