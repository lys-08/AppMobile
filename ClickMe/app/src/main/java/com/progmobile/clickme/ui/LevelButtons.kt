package com.progmobile.clickme.ui

import android.media.MediaPlayer
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.progmobile.clickme.R
import com.progmobile.clickme.data.DataSource.currentLevel

/**
 * Customizable button composable that displays the [labelResourceId]
 * and triggers [onClick] lambda when this composable is clicked
 */
@Composable
fun LevelButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    longClick: Boolean = false,
    inLevelButton: Boolean = false,
    modifier: Modifier = Modifier
) {
    // Long click duration
    val holdDuration = 4000L

    // Sound section
    val context = LocalContext.current
    var mediaPlayer: MediaPlayer? = null

    // Load the sound when the composable enters the composition
    DisposableEffect(Unit) {
        if (inLevelButton) {
            mediaPlayer = MediaPlayer.create(context, R.raw.click_button)
        } else {
            // TODO : Change the sound
            mediaPlayer = MediaPlayer.create(context, R.raw.click_button)
        }
        mediaPlayer?.isLooping = false
        mediaPlayer?.start()

        onDispose {
            mediaPlayer?.release() // Release the MediaPlayer when not needed
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp)) // Rounded corners
            // light blue background
            .background(Color(0xFFADD8E6))
            .padding(16.dp)       // Padding for content spacing
            // Clickable area with long click
            .pointerInput(longClick) {
                detectTapGestures(
                    onPress = {
                        if (longClick) {
                            // For long click: wait for hold duration
                            mediaPlayer?.start()
                            val success = tryAwaitRelease().also { kotlinx.coroutines.delay(holdDuration) }
                            if (success) onClick()
                        } else {
                            // For short click: trigger onClick immediately on press
                            mediaPlayer?.start()
                            onClick()
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center   // Center text in Box
    ) {
        Text(
            text = stringResource(labelResourceId),
            color = Color.White,
            //style = MaterialTheme.typography.button // Adjust font style as needed
        )
    }
}

@Composable
fun LevelButtonLocked(
    @StringRes labelResourceId: Int, // TODO : argument is passed but never used ?
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp),
        enabled = false
    ) {
        Image(
            painter = painterResource(id = R.drawable.lock_icon),
            contentDescription = "Locked levels",
            modifier = Modifier
                .background(Color.Transparent)
                .wrapContentSize(Alignment.Center)
        )
    }
}

/**
 * Composable that allows the user to select the desired action to do and triggers
 * [onUnlock] lambda when the level is unlocked, replaces the default onClick action of the this composable.
 */
@Composable
fun UnlockLevel(
    @StringRes labelResourceId: Int,
    level: Int,
    modifier: Modifier = Modifier,
    levelName: String,
    navController: NavHostController,
    longClick:Boolean = false,
    onUnlock: (() -> Unit)? = null
) {
    LevelButton(
        onClick = {
            if (onUnlock != null) {
                onUnlock()
            } else {
                navController.navigate(levelName)
                if (currentLevel < level) {
                    currentLevel++
                }
            }
        },
        labelResourceId = labelResourceId,
        longClick = longClick,
        inLevelButton = true,
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}