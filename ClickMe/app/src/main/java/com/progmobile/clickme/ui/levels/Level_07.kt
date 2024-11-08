package com.progmobile.clickme.ui.levels

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
import com.progmobile.clickme.data.DataSource.currentLevel
import com.progmobile.clickme.ui.LevelButton
import com.progmobile.clickme.ui.LevelButtonLocked
import com.progmobile.clickme.ui.UnlockLevel


/**
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
 */

@SuppressLint("ServiceCast")
@Composable
fun Level_07(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isCharging = remember { mutableStateOf(false) }

    // Creation of a BroadcastReceiver to check the battery state
    val batteryReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                isCharging.value = status == BatteryManager.BATTERY_STATUS_CHARGING
            }
        }
    }

    // Register the BroadcastReceiver
    DisposableEffect(Unit) {
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED) // Listen to the battery change state
        context.registerReceiver(batteryReceiver, filter) // Save the BroadcastReceiver with the context

        onDispose {
            context.unregisterReceiver(batteryReceiver)
        } // Free the resources
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.level_07),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        // Level button
        if (isCharging.value)
        {
            UnlockLevel(
                labelResourceId = R.string.button,
                level = 7,
                modifier,
                levelName = Screens.Level_08.name,
                navController
            )
        }
    }
}

@Preview
@Composable
fun StartLevel07Preview() {
    MaterialTheme {
        Level_07(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}