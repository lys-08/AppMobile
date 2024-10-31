package com.progmobile.clickme

import com.progmobile.clickme.service.MusicService
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.progmobile.clickme.ui.theme.ClickMeTheme

class MainActivity : ComponentActivity() {
    private var intent = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClickMeTheme {
                ClickMeApp()
            }
        }
        intent = Intent(this, MusicService::class.java)
        startService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(intent)
    }

    override fun onPause() {
        super.onPause()
        stopService(intent)
    }

    override fun onResume() {
        super.onResume()
        startService(intent)
    }
}