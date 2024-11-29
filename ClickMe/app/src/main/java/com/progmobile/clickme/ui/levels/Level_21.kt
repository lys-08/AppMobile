package com.progmobile.clickme.ui.levels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
import com.progmobile.clickme.data.DataSource.isAppInForeground
import com.progmobile.clickme.ui.LevelButton
import com.progmobile.clickme.ui.theme.ClickMeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
 */
@Composable
fun Level_21(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var timeCount by remember { mutableIntStateOf(0) }
    var showButton by remember { mutableStateOf(false) }
    val isForeground by remember { isAppInForeground }

    // =========== Monitoring Functions ===========

    fun startMonitoring() {
        // Start the coroutine
        CoroutineScope(Dispatchers.IO).launch {
            try {
                while (!showButton) {
                    timeCount++
                    if (!isForeground) {
                        timeCount = 0
                    }
                    if (timeCount > 20) {
                        showButton = true
                    }
                    delay(1000)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // =================== End ===================

    LaunchedEffect(Unit) {
        startMonitoring()
    }

    val fingerCount = remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val touches = event.changes.filter { it.pressed }

                        fingerCount.intValue = touches.size
                    }
                }
            }
    ) {
        if (fingerCount.intValue != 0) {
            timeCount = 0
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.level_21),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        Text(text = "lastTouchTime: $timeCount", style = MaterialTheme.typography.headlineMedium)

        // Level button
        if (showButton) {
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
fun StartLevel21Preview() {
    ClickMeTheme {
        Level_21(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}