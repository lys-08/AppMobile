package com.progmobile.clickme

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.progmobile.clickme.data.DataSource.MUSIC_DEFAULT
import com.progmobile.clickme.data.DataSource.SOUND_DEFAULT
import com.progmobile.clickme.data.DataSource.STARTING_LEVEL
import com.progmobile.clickme.ui.theme.ClickMeTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore("user_prefs")
private const val STARTING_LEVEL = 0
private const val MUSIC_DEFAULT = true
private const val SOUND_DEFAULT = true


class MainActivity : ComponentActivity() {

    // ========== PERMISSIONS ==========
    private var permissionsToCheck = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACTIVITY_RECOGNITION,
        /* Manifest.permission.ACCESS_FINE_LOCATION */) // TODO : GPS permission
    private val permissionsStatus = mutableStateOf(false) // state to check if a permission have been denied

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val anyPermissionDenied = permissions.values.any { !it }
        permissionsStatus.value = anyPermissionDenied
    }

    // ========== LEVEL ==========
    var currentLevelUnlocked by mutableIntStateOf(STARTING_LEVEL)

    // ========== MEDIA PLAYER ==========
    companion object {
        var instance: MainActivity? = null
    }

    //var intent = Intent()
    private var mediaPlayer: MediaPlayer? = null

    var userMusicPreference by mutableStateOf(MUSIC_DEFAULT)
    var userSoundPreference by mutableStateOf(SOUND_DEFAULT)

    // ========= STEP COUNT ===========
    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null
    private var totalSteps = 0f
    private var previousSteps = 0f

    // ========= MAIN ACTIVITY ==========
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this // Set the instance to the current activity
        enableEdgeToEdge()
        checkPermissions()

        setContent {
            ClickMeTheme {
                ClickMeApp(permissionsDenied = permissionsStatus, mainActivityInstance = this)
            }

        }
        //intent = Intent(this, MusicService::class.java)
        //startService(intent)

        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.background_music_1)
        mediaPlayer?.isLooping = true
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

        // Get music state in datastore : if true, user wants it on, then reactivate it.
        // If false, user wants it off, don't do anything as it has been paused at onPause or stopped at onDestroy
        userMusicPreference = runBlocking { getUserMusicPreference() }
        if (userMusicPreference) runBlocking { switchMusicState(stopMusic = false) }

        // Get sound state, for the buttons to know if they should play sound when clicked
        userSoundPreference = runBlocking { getUserSoundPreference() }

        // Get current level unlocked
        currentLevelUnlocked = runBlocking { getCurrentLevelUnlocked() }

    }

    private suspend fun getUserMusicPreference(): Boolean {
        val musicKey = booleanPreferencesKey(R.string.music_key.toString())
        val dataStore = this.dataStore
        // get boolean music state
        val musicState = dataStore.data.map { preferences ->
            preferences[musicKey] ?: MUSIC_DEFAULT
        }.first()
        return musicState
    }

    private suspend fun getUserSoundPreference(): Boolean {
        val soundKey = booleanPreferencesKey(R.string.sound_key.toString())
        val dataStore = this.dataStore
        // get boolean sound state
        val soundState = dataStore.data.map { preferences ->
            preferences[soundKey] ?: SOUND_DEFAULT
        }.first()
        return soundState
    }

    private suspend fun getCurrentLevelUnlocked(): Int {
        val levelKey = intPreferencesKey(R.string.level_key.toString())
        val dataStore = this.dataStore
        // get boolean music state
        val currentLevel = dataStore.data.map { preferences ->
            preferences[levelKey] ?: STARTING_LEVEL
        }.first()
        return currentLevel
    }

    fun switchMusicState(stopMusic: Boolean = false) {
        val musicKey = booleanPreferencesKey(R.string.music_key.toString())
        // normal scope not for composable
        if (stopMusic) {
            mediaPlayer?.pause()
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[musicKey] = false
                }
            }
            userMusicPreference = false
        } else {
            mediaPlayer?.start()
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[musicKey] = true
                }
            }
            userMusicPreference = true
        }
    }

    fun switchSoundState() {
        val soundKey = booleanPreferencesKey(R.string.sound_key.toString())
        // normal scope not for composable
        if (userSoundPreference) {
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[soundKey] = false
                }
            }
            userSoundPreference = false
        } else {
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[soundKey] = true
                }
            }
            userSoundPreference = true
        }
    }

    fun increaseLevel() {
        currentLevelUnlocked++
        val levelKey = intPreferencesKey(R.string.level_key.toString())
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences[levelKey] = currentLevelUnlocked
            }
        }
    }

    fun resetLevels() {
        currentLevelUnlocked = STARTING_LEVEL
        val levelKey = intPreferencesKey(R.string.level_key.toString())
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences[levelKey] = currentLevelUnlocked
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