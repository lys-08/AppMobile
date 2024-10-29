package com.progmobile.clickme.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens


@Composable
fun ClickMeBottomBar(
    levelHints: Map<String, List<String>>,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        containerColor = Color.Transparent
    ) {
        val currentRoute = navController.currentDestination?.route
        Row(
            modifier = Modifier
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
                    listOfHints = levelHints[currentRoute]
                        ?: emptyList(), // Pass hints for the current level
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
    modifier: Modifier = Modifier
){
    // Find if the system is in dark of light theme and color the buttons accordingly
    val isDarkTheme = isSystemInDarkTheme()
    val iconTintColor = if (isDarkTheme) Color.White else Color.Black
    Image(
        painter = painterResource(id = imageResourceId),
        contentDescription = contentDescription, // Provide content description for accessibility
        colorFilter = ColorFilter.tint(iconTintColor), // Tint the image to black
        modifier = Modifier
            .clickable(onClick = onClick) // Make the image clickable
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
    var isNotHomePage: Boolean = false
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
        modifier = Modifier
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
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true // This clears the back stack up to the start destination
                    }
                }
                showDialog = false
            },
            onMusicIconClick = { /* Handle music icon click */ }, // TODO : Add music
            onVolumeIconClick = { /* Handle volume icon click */ } // TODO : Add volume
        )
    }
}

@Composable
fun ParameterDialog(
    isNotHomePage: Boolean = true,
    onDismissRequest: () -> Unit,
    onNavigateToHomePage: () -> Unit,
    onMusicIconClick: () -> Unit,
    onVolumeIconClick: () -> Unit
) {
    var isMusicUp by remember { mutableStateOf(true) }
    var isVolumeUp by remember { mutableStateOf(true) }

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

            // Music icon
            IconButton(
                onClick = {
                    isMusicUp = !isMusicUp
                    onMusicIconClick()
                },
                imageResourceId = if (isMusicUp) R.drawable.music_up_icon else R.drawable.music_off_icon,
                contentDescription = if (isMusicUp) "Music Up" else "Music Off",
                modifier = Modifier
                    .widthIn(max = 128.dp)
            )

            // Volume icon
            IconButton(
                onClick = {
                    isVolumeUp = !isVolumeUp
                    onMusicIconClick()
                },
                imageResourceId = if (isVolumeUp) R.drawable.volume_up_icon else R.drawable.volume_off_icon,
                contentDescription = if (isVolumeUp) "Volume Up" else "Volume Off",
                modifier = Modifier
                    .widthIn(max = 128.dp)
            )
        }
    }
}

@Composable
fun HintIconButton(
    listOfHints: List<String>,
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
        modifier = Modifier
            .padding(16.dp)
            .size(48.dp) // Set the size of the image)
            .wrapContentSize(Alignment.BottomEnd)
    )

    // Show a dialog when showDialog is true
    if (showDialog) {
        SwipableDialog(
            onDismissRequest = { showDialog = false },
            listOfHints = listOfHints,
            navController = navController
        )
    }
}

@Composable
fun SwipableDialog(
    onDismissRequest: () -> Unit,
    listOfHints: List<String>,
    navController: NavHostController,
) {
    var isLevel10: Boolean = false
    if (navController.currentDestination?.route != Screens.HomePage.name) {
        isLevel10 = true
    }

    var mutableListOfHints by remember { mutableStateOf(listOfHints) }
    Dialog(onDismissRequest = onDismissRequest) {
        // If listOfHints is empty, display a message
        if (mutableListOfHints.isEmpty()) {
            mutableListOfHints = listOf("Sorry, no hint available")
        }

        // Create a pager to swipe between 3 elements
        val numberOfHints = mutableListOfHints.size
        val pagerState = rememberPagerState(){ numberOfHints }
        val coroutineScope = rememberCoroutineScope()

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
                    if (isLevel10 && page == mutableListOfHints.lastIndex) {
                        // Display a button on the last page
                        UnlockLevel(
                            onUnlock = {
                                // Trigger onDismissRequest after unlocking
                                onDismissRequest()
                            },
                            labelResourceId = R.string.button,
                            level = 1,
                            modifier = Modifier,
                            levelName = Screens.Level_01.name,
                            navController = navController
                        )
                    } else {
                        PageContent(
                            text = mutableListOfHints[page],
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
                   if (isLevel10 == false) {
                       repeat(numberOfHints) { pageIndex ->
                           val color =
                               if (pagerState.currentPage == pageIndex) Color.Black else Color.Gray
                           Box(
                               modifier = Modifier
                                   .size(12.dp)
                                   .background(color = color, shape = MaterialTheme.shapes.small)
                                   .padding(4.dp)
                           )
                       }
                   } else {

                   }
                }
            }
        }
    }
}