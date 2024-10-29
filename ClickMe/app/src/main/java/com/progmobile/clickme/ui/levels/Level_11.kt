package com.progmobile.clickme.ui.levels

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.BatteryManager
import androidx.compose.foundation.isSystemInDarkTheme
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
fun Level_11(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentMode = context.resources.configuration.uiMode
    val isDarkMode = remember { mutableStateOf(currentMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) }

    // Creation of a BroadcastReceiver to check the light mode state
    val modeReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val newMode = context?.resources?.configuration?.uiMode
                val isCurrentlyDark = newMode?.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
                isDarkMode.value = isCurrentlyDark
            }
        }
    }

    // Register the BroadcastReceiver
    DisposableEffect(Unit) {
        val filter = IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED) // Listen to the configuration change state
        context.registerReceiver(modeReceiver, filter) // Save the BroadcastReceiver with the new context
        onDispose {
            context.unregisterReceiver(modeReceiver) // Free the resources
        }
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
        if (isDarkMode.value)
        {
            LevelButton(
                labelResourceId = R.string.button,
                onClick = { navController.navigate(Screens.HomePage.name) },
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
fun StartLevel11Preview() {
    MaterialTheme {
        Level_11(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}