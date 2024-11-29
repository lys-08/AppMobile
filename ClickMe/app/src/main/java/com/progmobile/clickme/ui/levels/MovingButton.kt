package com.progmobile.clickme.ui.levels

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import com.progmobile.clickme.ui.theme.ClickMeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sqrt
import kotlin.random.Random

const val SHAKE_DIST = 10
const val SHAKE_TIME = 500
const val SHAKE_COUNT_THRESHOLD = 10
const val SHAKE_TIME_FRAME = 5000L

class ShakeDetector(
    context: Context,
    private val onShake: () -> Unit
) : SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var firstShakeTime: Long = 0
    private var lastShakeTime: Long = 0
    private var shakeCount: Int = 0

    fun start() {
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val x = it.values[0]
                val y = it.values[1]
                val z = it.values[2]

                val acceleration = sqrt(x * x + y * y + z * z)
                val currentTime = System.currentTimeMillis()

                if (acceleration > SHAKE_DIST && (currentTime - lastShakeTime > SHAKE_TIME)) {
                    // Get the first shake time
                    if (shakeCount == 0) {
                        firstShakeTime = currentTime
                    }

                    lastShakeTime = currentTime

                    // If current shake time is more than SHAKE_TIME_FRAME milliseconds after the first shake, reset the count
                    if (currentTime - firstShakeTime <= SHAKE_TIME_FRAME) {
                        shakeCount++

                        if (shakeCount >= SHAKE_COUNT_THRESHOLD) {
                            onShake()
                            reset()
                        }
                    }else {
                        reset()
                        // print a toast message
                        //Toast.makeText(this.context, "Count reset", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun reset(){
        shakeCount = 0
        firstShakeTime = 0
        lastShakeTime = 0
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No implementation needed
    }
}



/**
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
 */
@Composable
fun MovingButton(
    idLevel: Int,
    nextLevel: String,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var shakeDetected by rememberSaveable { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val shakeDetector = ShakeDetector(context) {
            shakeDetected = true
        }
        shakeDetector.start()

        onDispose {
            shakeDetector.stop()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
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

        if (shakeDetected) {
            // Level button
            UnlockLevel(
                labelResourceId = R.string.button,
                level = idLevel,
                modifier,
                levelName = nextLevel,
                navController
            )
        } else {
            MovingButtonApp()
        }
    }
}

@Composable
fun MovingButtonApp() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        SmoothMovingButton(100)
    }
}

@Composable
fun SmoothMovingButton(duration: Long) {
    val scope = rememberCoroutineScope()
    val positionX = remember { Animatable(0f) }
    val positionY = remember { Animatable(0f) }
    var parentSize by remember { mutableStateOf(IntSize(0, 0)) }
    var boxSize by remember { mutableStateOf(IntSize(0, 0)) }

    LaunchedEffect(parentSize) {
        while (parentSize.width > 0 && parentSize.height > 0 && boxSize.width > 0 && boxSize.height > 0) {
            val targetX = Random.nextFloat() * (parentSize.width - boxSize.width).coerceAtLeast(0)
            val targetY = Random.nextFloat() * (parentSize.height - boxSize.height).coerceAtLeast(0)

            scope.launch {
                positionX.animateTo(targetX, animationSpec = tween(durationMillis = duration.toInt()))
            }
            scope.launch {
                positionY.animateTo(targetY, animationSpec = tween(durationMillis = duration.toInt()))
            }

            delay(duration)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                parentSize = coordinates.size
            }
    ) {
        Box(
            modifier = Modifier
                .offset{
                    IntOffset(
                    x = positionX.value.toInt(),
                    y = positionY.value.toInt()
                    )
                }

                .onGloballyPositioned { coordinates ->
                    boxSize = coordinates.size // Capture the box size
                }
                //.align(Alignment.TopStart) // Align based on position
                .clip(RoundedCornerShape(24.dp)) // Rounded corners
                // light blue background
                .background(Color(0xFFADD8E6))
                //.size(squareSize.dp)
                //.fillMaxSize()
                .padding(16.dp)
                .wrapContentSize(Alignment.Center)

        ){
            Text(
                text = stringResource(R.string.button),
                color = Color.White,
                //style = MaterialTheme.typography.button // Adjust font style as needed
            )
        }
    }
}

@Preview
@Composable
fun StartLevel14Preview() {
    ClickMeTheme {
        Microphone(
            idLevel = -1,
            nextLevel = Screens.HomePage.name,
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}