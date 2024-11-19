package com.progmobile.clickme.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.MainActivity
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
import com.progmobile.clickme.data.DataSource

@Composable
fun ClickMeBottomBar(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        containerColor = Color.Transparent
    ) {
        val currentRoute = navController.currentDestination?.route
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // Apply padding to both left and right
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Left-aligned: ParameterIconButton
            ParameterIconButton(navController)

            // Only display HintIconButton if not on homepage
            if (currentRoute != Screens.HomePage.name) {
                // Right-aligned: HintIconButton
                HintIconButton(
                    navController = navController
                )
            }
        }
    }
}

/**
 * Customizable hint button composable that uses the [imageResourceId] icon
 * and triggers [onClick] lambda when this composable is clicked
 */
@Composable
fun IconButton(
    @DrawableRes imageResourceId: Int,
    onClick: () -> Unit,
    contentDescription : String? = null,
    bottomButton: Boolean = false,
    modifier: Modifier = Modifier
){
    // Find if the system is in dark of light theme and color the buttons accordingly
    val isDarkTheme = isSystemInDarkTheme()
    val colorFilter = if (bottomButton) {
        ColorFilter.tint(if (isDarkTheme) Color.White else Color.Black)
    } else {
        null
    }

    // Sound section
    val context = LocalContext.current

    // Add code to onClick
    Image(
        painter = painterResource(id = imageResourceId),
        contentDescription = contentDescription, // Provide content description for accessibility
        colorFilter = colorFilter, // Tint the image to black
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        if (MainActivity.instance?.userSoundPreference == true) {
                            val mediaPlayer = MediaPlayer.create(context, R.raw.click_button)
                            mediaPlayer.setOnCompletionListener { it.release() }
                            mediaPlayer.start()
                        }
                        onClick()
                    }
                )
            }
            // Make the image clickable
            .background(Color.Transparent) // Ensure no background color
            .wrapContentSize(Alignment.Center) // Center the image
    )
}

@Composable
fun ParameterIconButton(
    navController: NavController,
    modifier: Modifier = Modifier,
){
    // Maintain a state to control when the dialog should be shown
    var showDialog by remember { mutableStateOf(false) }

    // Initialize a var to check if we are on the homepage
    var isNotHomePage = false
    // Get the current route to determine which icon to show
    if (navController.currentDestination?.route != Screens.HomePage.name) {
        isNotHomePage = true
    }

    // IconButton with onClick to show dialog
    IconButton(
        imageResourceId = R.drawable.settings_icon,
        onClick = {
            showDialog = true
        },
        contentDescription = "Settings",
        bottomButton = true,
        modifier = modifier
            .padding(16.dp)
            .size(48.dp) // Set the size of the image)
            .wrapContentSize(Alignment.BottomEnd)
    )

    if(showDialog){
        ParameterDialog(
            isNotHomePage = isNotHomePage,
            onDismissRequest = { showDialog = false },
            onNavigateToHomePage = {
                navController.navigate(Screens.HomePage.name){
                    popUpTo(0) { inclusive = true } // This clears the entire stack
                    launchSingleTop = true         // Prevents multiple instances of HomePage
                }
                showDialog = false
            }
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ParameterDialog(
    isNotHomePage: Boolean = true,
    onDismissRequest: () -> Unit,
    onNavigateToHomePage: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isNotHomePage) {
                // Home button
                IconButton(
                    onClick = onNavigateToHomePage,
                    imageResourceId = R.drawable.home_icon,
                    contentDescription = "Home",
                    modifier = Modifier
                        .widthIn(max = 128.dp)
                )
            }

            // MUSIC ICON
            // Do a safe call
            var isMusicUp = MainActivity.instance?.userMusicPreference == true
            IconButton(
                onClick = {
                    MainActivity.instance?.switchMusicState(stopMusic = isMusicUp)
                    // switch Music State locally to know which icon to display below
                    // isMusicUp value will be crushed next call to this composable
                    isMusicUp = !isMusicUp
                },
                imageResourceId = if (isMusicUp) R.drawable.music_up_icon else R.drawable.music_off_icon,
                contentDescription = if (isMusicUp) "Music Up" else "Music Off",
                modifier = Modifier
                    .widthIn(max = 128.dp)
            )

            // VOLUME ICON
            var isVolumeUp = MainActivity.instance?.userSoundPreference == true
            IconButton(
                onClick = {
                    MainActivity.instance?.switchSoundState()
                    // same explication than for music
                    isVolumeUp = !isVolumeUp
                },
                imageResourceId = if (isVolumeUp) R.drawable.volume_up_icon else R.drawable.volume_off_icon,
                contentDescription = if (isVolumeUp) "Volume Up" else "Volume Off",
                modifier = Modifier
                    .widthIn(max = 128.dp)
            )

            // REINITIALIZE LEVELS ICON
            val context = LocalContext.current
            if (!isNotHomePage) {
                IconButton(
                    onClick = {
                        // display a confirmation dialog
                        showConfirmationDialog(context)
                    },
                    imageResourceId = R.drawable.restart_icon,
                    contentDescription = "Restart",
                    modifier = Modifier
                        .widthIn(max = 128.dp)
                )
            }
        }
    }
}

@Composable
fun HintIconButton(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // Maintain a state to control when the dialog should be shown
    var showDialog by remember { mutableStateOf(false) }

    // IconButton with onClick to show dialog
    IconButton(
        imageResourceId = R.drawable.interrogation,
        onClick = {
            showDialog = true
        },
        contentDescription = "Hint",
        bottomButton = true,
        modifier = modifier
            .padding(16.dp)
            .size(48.dp) // Set the size of the image)
            .wrapContentSize(Alignment.BottomEnd)
    )

    // Show a dialog when showDialog is true
    if (showDialog) {
        SwipableDialog(
            onDismissRequest = { showDialog = false },
            navController = navController
        )
    }
}

@Composable
fun SwipableDialog(
    onDismissRequest: () -> Unit,
    navController: NavHostController,
) {
    val context = LocalContext.current

    var isLevel10 = false
    if (navController.currentDestination?.route == Screens.Level_10.name) {
        isLevel10 = true
    }

    val listOfHints = DataSource.levelHints[navController.currentDestination?.route]
    var mutableListOfHints by remember { mutableStateOf(listOf<Int>()) }
    if (listOfHints == null) {
        mutableListOfHints = listOf(R.string.hint_00)
    } else {
        mutableListOfHints = listOfHints
    }

    Dialog(onDismissRequest = onDismissRequest) {
        // If listOfHints is empty, display a message

        // Create a pager to swipe between 3 elements
        var numberOfHints = mutableListOfHints.size
        if (isLevel10) {
            numberOfHints++
        }
        val pagerState = rememberPagerState { numberOfHints }

        Box(
            modifier = Modifier
                .size(300.dp, 300.dp) // Set dialog size
                .background(Color.White) // Set dialog background
                .padding(16.dp) // Add padding
        ) {
            Column {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f) // Take remaining space
                ) { page ->
                    // Dynamically display content based on the current page
                    if (isLevel10 && page == mutableListOfHints.lastIndex + 1) {
                        // Display a button on the last page
                        UnlockLevel(
                            onUnlock = {
                                // Trigger onDismissRequest after unlocking
                                onDismissRequest()
                                navController.navigate(Screens.Level_11.name)
                                if (MainActivity.instance?.currentLevelUnlocked!! < 11) {
                                    MainActivity.instance?.increaseLevel()
                                }
                            },
                            labelResourceId = R.string.button,
                            level = 10,
                            modifier = Modifier,
                            levelName = Screens.Level_09.name,
                            navController = navController
                        )
                    } else {
                        PageContent(
                            text = context.getString(mutableListOfHints[page]),
                            backgroundColor = Color.Transparent
                        )
                    }
                }

                // Add a row of dots to indicate which page is active
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    repeat(numberOfHints) { pageIndex ->
                        val color =
                            if (pagerState.currentPage == pageIndex) Color.Black else Color.Gray
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(color = color, shape = MaterialTheme.shapes.small)
                                .padding(8.dp)
                                .padding(horizontal = 16.dp)
                        )

                        // Add Spacer with desired width
                        if (pageIndex < numberOfHints - 1) {
                            Spacer(modifier = Modifier.width(8.dp)) // Adjust the width as needed
                        }
                    }
                }
            }
        }
    }
}

fun showConfirmationDialog(context: Context) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(R.string.reset_levels_title)
    builder.setMessage(R.string.reset_levels_message)
    builder.setPositiveButton(R.string.reset_levels_positive_button) { dialog, which ->
        // User clicked Yes button, call resetLevels()
        MainActivity.instance?.resetLevels()
    }
    builder.setNegativeButton(R.string.reset_levels_negative_button, null) // Do nothing on No click
    val dialog = builder.create()
    dialog.show()
}