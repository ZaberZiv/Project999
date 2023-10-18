package com.izaber.project999

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.izaber.project999.app.ActivityCallback
import com.izaber.project999.app.App
import com.izaber.project999.app.MainRepresentative

class MainActivity : AppCompatActivity() {

    private lateinit var mainRepresentative: MainRepresentative
    private lateinit var textView: TextView
    private lateinit var button: Button
    private lateinit var activityCallback: ActivityCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainRepresentative = (application as App).mainRepresentative
        textView = findViewById(R.id.tvTitle)
        button = findViewById<AppCompatButton>(R.id.button_show_toast)
        activityCallback = object : ActivityCallback {
            override fun isEmpty() = false

            override fun update(data: Int) = runOnUiThread {
                textView.setText(data)
            }
        }

        if (savedInstanceState == null)
            textView.text = "0"

        button.setOnClickListener {
            mainRepresentative.startAsync()
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
}