package com.progmobile.clickme.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

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