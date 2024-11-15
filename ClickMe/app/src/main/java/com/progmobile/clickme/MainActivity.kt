package com.progmobile.clickme

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.progmobile.clickme.ui.theme.ClickMeTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore("user_prefs")

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

    // ========== LEVEL ==========
    var currentLevel by mutableIntStateOf(0)

    // ========== MEDIA PLAYER ==========
    companion object {
        var instance: MainActivity? = null
    }

    //var intent = Intent()
    private var mediaPlayer: MediaPlayer? = null

    // Getter for mediaPlayer?.isPlaying
    fun isMusicPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    var isSoundOn by mutableStateOf(false)

    // ========= MAIN ACTIVITY ==========
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this // Set the instance to the current activity
        enableEdgeToEdge()
        checkPermissions()
        setContent {
            ClickMeTheme {
                ClickMeApp(permissionsStatus)
            }
            currentLevel = 11
            DataStoreApp(currentLevel)
            isSoundOn = true

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
        // Stop the music when the app is paused
        mediaPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            if (getMusicState()) {
                switchMusicState()
            }
        }
    }

    suspend fun getMusicState(): Boolean {
        var musicState by mutableStateOf(false)
        val musicKey = booleanPreferencesKey(R.string.music_key.toString())
        val dataStore = this.dataStore
        // get boolean music state
        musicState = dataStore.data.map { preferences ->
            preferences[musicKey] ?: false
        }.first()
        return musicState
    }

    fun switchMusicState() {
        val musicKey = booleanPreferencesKey(R.string.music_key.toString())
        // normal scope not for composable
        if (isMusicPlaying()) {
            mediaPlayer?.pause()
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[musicKey] = false
                }
            }
        } else {
            mediaPlayer?.start()
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[musicKey] = true
                }
            }
        }
    }

    fun switchSoundState() {
        val soundKey = booleanPreferencesKey(R.string.sound_key.toString())
        // normal scope not for composable
        if (isSoundOn) {
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[soundKey] = false
                }
            }
            isSoundOn = false
        } else {
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[soundKey] = true
                }
            }
            isSoundOn = true
        }
    }

    fun increaseLevel() {
        currentLevel++
        val levelKey = intPreferencesKey(R.string.level_key.toString())
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences[levelKey] = currentLevel
            }
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

@Composable
fun DataStoreApp(currentLevel: Int) {
    val context = LocalContext.current
    val musicKey = booleanPreferencesKey(R.string.music_key.toString())
    val soundKey = booleanPreferencesKey(R.string.sound_key.toString())
    val levelKey = intPreferencesKey(R.string.level_key.toString())

    val dataStore = context.dataStore
    dataStore.data.map { preferences ->
        preferences[musicKey] ?: false
        preferences[soundKey] ?: false
        preferences[levelKey] ?: currentLevel
    }.collectAsState(initial = "")
}