package com.izaber.project999.subscription.data

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.izaber.project999.R
import com.izaber.project999.core.ProvideRepresentative
import com.izaber.project999.main.MainActivity
import com.izaber.project999.subscription.presentation.SubscriptionRepresentative
import kotlinx.coroutines.delay

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

    class Notif(
        private val workManager: WorkManager
    ) : ForegroundServiceWrapper {
        override fun start() {
            val work = OneTimeWorkRequestBuilder<NotifWork>().build()
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
            SubscriptionRepresentative::class.java
        )
        representative.subscribeInternal()
        return Result.success()
    }
}

class NotifWork(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    val nm by lazy { getSystemService(appContext, NotificationManager::class.java) }

    override suspend fun doWork(): Result {
        setForeground(createForegroundInfo())
        repeat(11) { value ->
            delay(1000)
            val prog = 10 * value
            updateNotification(prog)
        }
        return Result.success()
    }

    fun createForegroundInfo(): ForegroundInfo {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel",
                "Run Notif",
                NotificationManager.IMPORTANCE_HIGH
            )
            nm?.createNotificationChannel(channel)
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            ForegroundInfo(
                1,
                createNotification(0),
                FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        else
            ForegroundInfo(1, createNotification(0))
    }

    fun createNotification(counter: Int): Notification {
        val intent = Intent(applicationContext, MainActivity::class.java)
        val pending = TaskStackBuilder.create(applicationContext)
            .addNextIntentWithParentStack(intent)
            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        return NotificationCompat.Builder(applicationContext, "channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Test Notification")
            .setContentText("This is Notif from foreground!")
            .setProgress(100, counter, false)
            .setSound(null)
            .setOngoing(true)
            .setContentIntent(pending)
            .build()
    }

    private fun updateNotification(progress: Int) {
        val notification = createNotification(progress)
        nm?.notify(1, notification)
    }
}
