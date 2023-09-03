package com.izaber.project999

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<AppCompatButton>(R.id.button_show_toast)
        btn.setOnClickListener {

        }
        Toast.makeText(this@MainActivity, "Hello world", Toast.LENGTH_LONG).show()
    }
}