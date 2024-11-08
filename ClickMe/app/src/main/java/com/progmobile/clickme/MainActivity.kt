package com.progmobile.clickme

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.progmobile.clickme.ui.theme.ClickMeTheme

class MainActivity : ComponentActivity() {
    companion object {
        var instance: MainActivity? = null
    }

    //var intent = Intent()
    private var mediaPlayer: MediaPlayer? = null
    var stopFromAppBar:Boolean = false

    // Getter for mediaPlayer?.isPlaying
    fun isMusicPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this // Set the instance to the current activity
        enableEdgeToEdge()
        setContent {
            ClickMeTheme {
                ClickMeApp()
            }
        }
        //intent = Intent(this, MusicService::class.java)
        //startService(intent)

        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.background_music_1)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

    }

    override fun onDestroy() {
        super.onDestroy()
        //stopService(intent)
        mediaPlayer?.release()
        mediaPlayer = null
        instance = null // Set the instance to null to avoid memory leaks
    }

    override fun onPause() {
        super.onPause()
        if(stopFromAppBar == false) {
            mediaPlayer?.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if(stopFromAppBar == false) {
            mediaPlayer?.start()
        }
    }

    fun switchMusicState() {
        if (isMusicPlaying() == true) {
            mediaPlayer?.pause()
            stopFromAppBar = true
        } else {
            mediaPlayer?.start()
            stopFromAppBar = false
        }
    }
}