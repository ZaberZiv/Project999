package com.izaber.project999.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.work.WorkManager
import com.izaber.project999.R
import com.izaber.project999.core.ActivityCallback
import com.izaber.project999.core.ProvideRepresentative
import com.izaber.project999.core.Representative
import com.izaber.project999.subscription.data.ForegroundServiceWrapper
import com.izaber.project999.utils.TAG

class MainActivity : AppCompatActivity(), ProvideRepresentative {

    private lateinit var mainRepresentative: MainRepresentative
    private lateinit var activityCallback: ActivityCallback

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.v(TAG, "MainActivity created (savedInstanceState: $savedInstanceState)")

        setContentView(R.layout.activity_main)
        requestNotificationPermission()
        mainRepresentative = provideRepresentative(MainRepresentative::class.java)

        activityCallback = object : ActivityCallback {
            override fun update(data: Screen) = runOnUiThread {
                data.show(supportFragmentManager, R.id.container)
                data.observed(mainRepresentative)
            }
        }

        mainRepresentative.showDashboard(savedInstanceState == null)

        val button = findViewById<Button>(R.id.startButton)
        val stopButton = findViewById<Button>(R.id.stopButton)

        button.setOnClickListener {
//            representative.play()
            Log.e("Ilya", "navigate to subscribe")

//            Intent(applicationContext, MyCameraService::class.java).also {
//                it.action = "START"
//                startService(it)
//            }
            ForegroundServiceWrapper.Notif(WorkManager.getInstance(this)).start()
        }

        stopButton.setOnClickListener {
//            representative.play()
            Log.e("Ilya", "stopButton")
//
//            Intent(applicationContext, MyCameraService::class.java).also {
//                it.action = "STOP"
//                startService(it)
//            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    override fun onResume() {
        super.onResume()
        mainRepresentative.startGettingUpdates(activityCallback)
    }

    override fun onPause() {
        super.onPause()
        mainRepresentative.stopGettingUpdates()
    }

    override fun <T : Representative<*>> provideRepresentative(clazz: Class<T>): T {
        return (application as ProvideRepresentative).provideRepresentative(clazz)
    }
}