package com.izaber.project999

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyCameraService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("Iya", "intent?.action -> ${intent?.action}")

        when (intent?.action) {
            "START" -> startForeground()
            "STOP" -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private var counter = 0
    val nm by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    private fun startForeground() {
        startForeground(1, createNotification(counter))

        CoroutineScope(Dispatchers.Default).launch {
            repeat(11) { value ->
                delay(1000)
                val prog = 10 * value
                updateNotification(prog)
                counter += prog
            }
            stopSelf()
        }
    }

    fun createNotification(counter: Int): Notification {
        return NotificationCompat.Builder(this, "channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Test Notification")
            .setContentText("This is Notif from foreground!")
            .setProgress(100, counter, false)
            .setSound(null)
            .build()
    }

    private fun updateNotification(progress: Int) {
        val notification = createNotification(progress)
        nm.notify(1, notification)
    }
}