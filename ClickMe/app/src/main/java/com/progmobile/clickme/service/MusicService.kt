package com.progmobile.clickme.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.progmobile.clickme.R

/**
 * DEPRECATED : MusicService is no longer used
 */

class MusicService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private var isPaused = false


    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music_1) // add your audio file in res/raw folder
        mediaPlayer.isLooping = true // Set looping
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!mediaPlayer.isPlaying) {
            if (isPaused) {
                mediaPlayer.start() // Resume if previously paused
                isPaused = false
            } else {
                mediaPlayer.start() // Start fresh if first time
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release() // Release resources when service is destroyed
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}
