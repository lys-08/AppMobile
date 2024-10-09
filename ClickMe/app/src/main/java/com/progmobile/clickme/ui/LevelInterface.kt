package com.progmobile.clickme.ui

import androidx.compose.ui.window.Dialog
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.progmobile.clickme.R

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
    Image(
        painter = painterResource(id = imageResourceId),
        contentDescription = contentDescription, // Provide content description for accessibility
        modifier = Modifier
            .clickable(onClick = onClick) // Make the image clickable
            .background(Color.Transparent) // Ensure no background color
            .wrapContentSize(Alignment.Center) // Center the image
    )
}

@Composable
fun HintIconButton(
    listOfHints: List<String>,
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
        /**
        BubbleDialog(
            onDismissRequest = { showDialog = false }, // Close dialog when dismissed
            hintText = hintText
        )
        */
        SwipableDialog(
            onDismissRequest = { showDialog = false },
            listOfHints = listOfHints
        )
    }
}

@Composable
fun ParameterIconButton(
    modifier: Modifier = Modifier,
){
    // Maintain a state to control when the dialog should be shown
    var showDialog by remember { mutableStateOf(false) }

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
            onDismissRequest = { showDialog = false },
            onNavigateToHomePage = { showDialog = false }, // TODO : Bring to home page
            onMusicIconClick = { /* Handle music icon click */ }, // TODO : Add music
            onVolumeIconClick = { /* Handle volume icon click */ } // TODO : Add volume
        )
    }
}

@Composable
fun ParameterDialog(
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
            // Home button
            IconButton(
                onClick = onNavigateToHomePage,
                imageResourceId = R.drawable.home_icon,
                contentDescription = "Home",
                modifier = Modifier
                    .widthIn(max = 128.dp)
            )

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
fun BubbleDialog(
    onDismissRequest: () -> Unit,
    hintText: String
) {
    Dialog(
        onDismissRequest = onDismissRequest // Dismiss when user interacts outside dialog
    ) {
        // Bubble dialog UI
        Box(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp) // Rounded corners for bubble effect
                )
                .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(
                text = hintText,
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun SwipableDialog(
    onDismissRequest: () -> Unit,
    listOfHints: List<String>,
    ) {
    Dialog(onDismissRequest = onDismissRequest) {
        // Create a pager to swipe between 3 elements
        val pagerState = rememberPagerState(){ 3 }
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
                    when (page) {
                        // TODO : Make this a for loop of n pages with a parameter
                        0 -> PageContent(text = listOfHints[0], backgroundColor = Color.Transparent)
                        1 -> PageContent(text = listOfHints[1], backgroundColor = Color.Transparent)
                        2 -> PageContent(text = listOfHints[2], backgroundColor = Color.Transparent)
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
                    repeat(3) { pageIndex ->
                        val color = if (pagerState.currentPage == pageIndex) Color.Black else Color.Gray
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(color = color, shape = MaterialTheme.shapes.small)
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PageContent(text: String, backgroundColor: Color) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

/**
WORK IN PROGRESS COMPOSABLES TO FORM THE HINT BOX AS A BUBBLE
@Composable
fun CloudDialogWithImage(onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Box(
            modifier = Modifier
                .size(200.dp) // Adjust size to your needs
                .background(Color.Transparent) // No background to let cloud show
        ) {
            // Display the cloud image
            Image(
                painter = painterResource(id = R.drawable.thought_bubble), // Use your cloud drawable resource
                contentDescription = "Cloud Icon",
                modifier = Modifier
                    .fillMaxSize()
            )

            // Overlay text inside the cloud
            Box(
                modifier = Modifier
                    .align(Alignment.Center) // Center the text inside the cloud
            ) {
                Text(
                    text = "This is a cloud dialog",
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}


@Composable
fun BubbleShape() {
    Canvas(
        modifier = Modifier
            .size(200.dp)
    ) {
        val cloudColor = Color.White
        val shadowColor = Color.Gray.copy(alpha = 0.3f)

        // Draw cloud shadow (for a 3D effect)
        drawCircle(
            color = shadowColor,
            radius = 90f,
            center = Offset(x = size.width * 0.5f + 10f, y = size.height * 0.5f + 10f)
        )

        // Draw main cloud circles
        drawCircle(
            color = cloudColor,
            radius = 70f,
            center = Offset(x = size.width * 0.3f, y = size.height * 0.5f)
        )
        drawCircle(
            color = cloudColor,
            radius = 80f,
            center = Offset(x = size.width * 0.5f, y = size.height * 0.4f)
        )
        drawCircle(
            color = cloudColor,
            radius = 60f,
            center = Offset(x = size.width * 0.7f, y = size.height * 0.5f)
        )
        drawCircle(
            color = cloudColor,
            radius = 50f,
            center = Offset(x = size.width * 0.5f, y = size.height * 0.7f)
        )
    }
}
*/

interface LevelInterface {
}