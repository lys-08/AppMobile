package com.progmobile.clickme

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import com.progmobile.clickme.ui.theme.ClickMeTheme
import java.util.logging.Handler

class MainActivity : ComponentActivity() {
  
    // ========== PERMISSIONS ==========
    private var permissionsToCheck = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        /* Manifest.permission.ACCESS_FINE_LOCATION */) // TODO : GPS permission
    private val permissionsStatus = mutableStateOf(false) // state to check if a permission have been denied

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val anyPermissionDenied = permissions.values.any { !it }
        permissionsStatus.value = anyPermissionDenied
    }

    // ========== MEDIA PLAYER ==========
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
        checkPermissions()
        setContent {
            ClickMeTheme {
                ClickMeApp(permissionsStatus)
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

    private fun checkPermissions() {
        // Check current permissions
        val permissionsNeeded = permissionsToCheck.filter { permission ->
            ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
        }

        // Needed permissions
        if (permissionsNeeded.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsNeeded.toTypedArray())
        } else {
            permissionsStatus.value = false
        }

        // Check if at least one permission have been denied
        val anyPermissionDeniedPreviously = permissionsToCheck.any { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED
        }

        // Update the status is a permission have been denied
        if (anyPermissionDeniedPreviously) {
            permissionsStatus.value = true
        }
    }
}