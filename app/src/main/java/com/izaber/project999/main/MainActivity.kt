package com.izaber.project999.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.izaber.project999.R
import com.izaber.project999.core.ActivityCallback
import com.izaber.project999.core.App
import com.izaber.project999.core.ProvideRepresentative
import com.izaber.project999.core.Representative

class MainActivity : AppCompatActivity(), ProvideRepresentative {

    private lateinit var mainRepresentative: MainRepresentative
    private lateinit var activityCallback: ActivityCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainRepresentative = provideRepresentative(MainRepresentative::class.java)

        activityCallback = object : ActivityCallback {
            override fun update(data: Screen) = runOnUiThread {
                data.show(supportFragmentManager, R.id.container)
            }
        }

        mainRepresentative.showDashboard(savedInstanceState == null)
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