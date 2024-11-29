package com.progmobile.clickme.ui.levels

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
import com.progmobile.clickme.ui.UnlockLevel
import com.progmobile.clickme.ui.theme.ClickMeTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "device_prefs")
val IS_DEVICE_SHUTDOWN_KEY = booleanPreferencesKey("isDeviceShutdown")

/**
 * Composable that displays the next level button when the device is shutdown once.
 * It uses a [UnlockLevel] composable to display the next level button.
 */
@Composable
fun Level_19(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val isDeviceShutdown = remember { mutableStateOf(false) }

    // Get the device state from the dataStore
    suspend fun getDeviceShutdownState(): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[IS_DEVICE_SHUTDOWN_KEY] ?: false
    }

    // Update dataStore
    suspend fun setDeviceShutdownState(isShutdown: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DEVICE_SHUTDOWN_KEY] = isShutdown
        }
    }

    val deviceReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    Intent.ACTION_SHUTDOWN -> {
                        coroutineScope.launch {
                            setDeviceShutdownState(true)
                        }
                    }
                }
            }
        }
    }

    DisposableEffect(Unit) {
        val filter = IntentFilter().apply { addAction(Intent.ACTION_SHUTDOWN) }
        context.registerReceiver(deviceReceiver, filter)
        onDispose {
            context.unregisterReceiver(deviceReceiver)
        }
    }

    LaunchedEffect(Unit) {
        isDeviceShutdown.value = getDeviceShutdownState()
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.level_19),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        // Level button
        if (isDeviceShutdown.value) {
            UnlockLevel(
                labelResourceId = R.string.button,
                level = 19,
                modifier = Modifier,
                levelName = Screens.Level_20.name,
                navController = navController
            )
        }
    }
}

@Preview
@Composable
fun StartLevel19Preview() {
    ClickMeTheme {
        Level_19(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}