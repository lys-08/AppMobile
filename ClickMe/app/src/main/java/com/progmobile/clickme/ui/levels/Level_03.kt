package com.progmobile.clickme.ui.levels

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.MainActivity
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
import com.progmobile.clickme.ui.UnlockLevel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Composable that displays the level with two buttons scren.
 * Uses two [UnlockLevel] buttons with a checkBothPressed() function to navigate to the next level if the buttons are both pressed at the same time.
 */
@Composable
fun Level_03(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.level_03),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        // Dual buttons
        var box1Pressed by remember { mutableStateOf(false) }
        var box2Pressed by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        // Music stuff, overriden here to avoid playing it two times
        val soundResourceId = R.raw.victory_sound
        val mediaPlayer = MediaPlayer.create(LocalContext.current, soundResourceId)
        val onBothClicked = {
            // Play music
            scope.launch {
                if (MainActivity.instance?.userSoundPreference == true) {
                    mediaPlayer.setOnCompletionListener { mediaPlayer.release() }
                    mediaPlayer.start()
                }
            }

            // Navigate to level 4
            navController.navigate(Screens.Level_04.name)
            if (MainActivity.instance?.currentLevelUnlocked!! < 3) {
                MainActivity.instance?.increaseLevel()
            }
        }

        // Helper function to check both states and trigger action
        fun checkBothPressed() {
            if (box1Pressed && box2Pressed) {
                onBothClicked()
                box1Pressed = false
                box2Pressed = false
            } else {
                // If only one button is pressed, start a coroutine to reset the state after a delay
                scope.launch {
                    delay(10) // 10 ms timeout for the second button to be pressed
                    box1Pressed = false
                    box2Pressed = false
                }
            }
        }

        // Level buttons
        Row {
            UnlockLevel(
                labelResourceId = R.string.button,
                level = 3,
                modifier = Modifier.weight(1f),
                levelName = Screens.Level_03.name,
                navController = navController,
                onUnlock = {
                    box1Pressed = true
                    checkBothPressed()
                },
                // disable music on the button Composable to avoid playing it two times
                // Play it here instead at CheckBothPressed() completion
                 playMusic = false
            )

            UnlockLevel(
                labelResourceId = R.string.button,
                level = 3,
                modifier = Modifier.weight(1f),
                levelName = Screens.Level_04.name,
                navController = navController,
                onUnlock = {
                    box2Pressed = true
                    checkBothPressed()
                },
                playMusic = false
            )
        }
    }
}

@Preview
@Composable
fun StartLevel03Preview() {
    MaterialTheme {
        Level_03(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}