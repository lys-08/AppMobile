package com.progmobile.clickme.ui

import android.media.MediaPlayer
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.R
import com.progmobile.clickme.data.DataSource.currentLevel
import com.progmobile.clickme.data.DataSource.levels
import kotlinx.coroutines.delay


/**
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
 */
@Composable
fun HomePage(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        // Levels button
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
          ) {
              //Enable buttons from unlocked levels
              items((0..currentLevel).toList()) { i ->
                  LevelButton(
                      labelResourceId = levels[i].first,
                      onClick = { navController.navigate(levels[i].second) }
                  )
              }
              //Disable buttons from locked levels
              items((currentLevel + 1..<levels.size).toList()) { i ->
                  LevelButtonLocked(
                      labelResourceId = levels[i].first,
                      onClick = { navController.navigate(levels[i].second) }
                  )
              }
        }

    }
}

/**
 * Customizable button composable that displays the [labelResourceId]
 * and triggers [onClick] lambda when this composable is clicked
 */
@Composable
fun LevelButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    longClick: Boolean = false,
    modifier: Modifier = Modifier
) {

    // Sound section
    val context = LocalContext.current
    var mediaPlayer: MediaPlayer? = null

    // Load the sound when the composable enters the composition
    DisposableEffect(Unit) {
        mediaPlayer = MediaPlayer.create(context, R.raw.click_button)
        if (mediaPlayer == null) {
            Log.e("SoundButton", "MediaPlayer initialization failed")
        }
        onDispose {
            mediaPlayer?.release() // Release the MediaPlayer when not needed
        }
    }

    val holdDuration = 4000L
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp)) // Rounded corners
            // light blue background
            .background(Color(0xFFADD8E6))
            /*
            .clickable(
                onClick = onClick,
                onLongClick = onClick,
            )     // Click action*/
            .pointerInput(longClick) {
                detectTapGestures(
                    onPress = {
                        if (longClick) {
                            // For long click: wait for hold duration
                            val success = tryAwaitRelease().also { delay(holdDuration) }
                            if (success) onClick(); mediaPlayer?.start()
                        } else {
                            // For short click: trigger onClick immediately on press
                            try {
                                mediaPlayer?.start()
                            } catch (e: Exception) {
                                Log.e("SoundButton", "Error playing sound: ${e.message}")
                            }

                            onClick()
                        }
                    }
                )
            }
            .padding(16.dp),                  // Padding for content spacing
        contentAlignment = Alignment.Center   // Center text in Box
    ) {
        Text(
            text = stringResource(labelResourceId),
            color = Color.White,
            //style = MaterialTheme.typography.button // Adjust font style as needed
        )
    }
    /*
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 150.dp, max = 150.dp)
        ) {
        Text(stringResource(labelResourceId))
    }
    */
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
        labelResourceId = labelResourceId,
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
        longClick = longClick,
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}


@Preview
@Composable
fun StartHomePagePreview() {
    MaterialTheme {
        HomePage(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}