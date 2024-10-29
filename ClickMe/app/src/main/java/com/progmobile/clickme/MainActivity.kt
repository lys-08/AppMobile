package com.progmobile.clickme

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import com.progmobile.clickme.ui.theme.ClickMeTheme

class MainActivity : ComponentActivity() {
    private var permissionsToCheck = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        /* Manifest.permission.ACCESS_FINE_LOCATION */) // TODO : GPS permission
    private val permissionsStatus = mutableStateOf(false) // state to check if a permission have been denied

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val anyPermissionDenied = permissions.values.any { !it }
        permissionsStatus.value = anyPermissionDenied
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        checkPermissions()
        setContent {
            ClickMeTheme {
                ClickMeApp(permissionsStatus)
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