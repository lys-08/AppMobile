package com.progmobile.clickme.ui.levels

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
import com.progmobile.clickme.ui.UnlockLevel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


/**
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
 */
@Composable
fun Level_14(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.level_14),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        // Level button
        UnlockLevel(
            labelResourceId = R.string.button,
            level = 14,
            modifier,
            levelName = Screens.Level_15.name,
            navController
        )

        // loop and every time move it by a random amount
        MovingSquareApp()
    }
}

@Composable
fun MovingSquareApp() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SmoothMovingSquare(333, 100)
    }
}

@Composable
fun SmoothMovingSquare(duration: Long, squareSize: Int) {
    val scope = rememberCoroutineScope()
    val positionX = remember { Animatable(0f) }
    val positionY = remember { Animatable(0f) }
    var parentSize by remember { mutableStateOf(IntSize(0, 0)) }

    LaunchedEffect(parentSize) {
        while (parentSize.width > 0 && parentSize.height > 0) {
            val targetX = Random.nextFloat() * (parentSize.width - squareSize)
            val targetY = Random.nextFloat() * (parentSize.height - squareSize)

            positionX.animateTo(
                targetValue = targetX,
                animationSpec = tween(durationMillis = duration.toInt())
            )
            positionY.animateTo(
                targetValue = targetY,
                animationSpec = tween(durationMillis = duration.toInt())
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                parentSize = coordinates.size
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                color = Color.Red,
                topLeft = Offset(positionX.value, positionY.value),
                size = androidx.compose.ui.geometry.Size(squareSize.toFloat(), squareSize.toFloat())
            )
        }
    }
}

@Preview
@Composable
fun StartLevel14Preview() {
    MaterialTheme {

        Level_08(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}