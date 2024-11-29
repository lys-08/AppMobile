package com.progmobile.clickme.ui.levels

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
import com.progmobile.clickme.data.DataSource.LEVEL_STEP_COUNT_STEP_THRESHOLD
import com.progmobile.clickme.ui.UnlockLevel
import com.progmobile.clickme.ui.theme.ClickMeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Class that handles step counting using the Android SensorManager
 */
class StepCounter(context: Context) : SensorEventListener {
    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepCounterSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    private var totalSteps = 0f
    private var previousSteps = 0f

    // Expose step count as a StateFlow for observing changes
    private val _stepCount = MutableStateFlow(0)
        val stepCount: StateFlow<Int> get() = _stepCount

    // Start listening to step counter sensor
    fun start() {
        stepCounterSensor?.let { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    // Stop listening to step counter sensor
    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            if (previousSteps == 0f) {
                // Initialize previousSteps to the first recorded step count
                previousSteps = event.values[0]
            }
            totalSteps = event.values[0]
            _stepCount.value = (totalSteps - previousSteps).toInt()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used in this example
    }
}

/**
 * Composable that display a level that counts user steps.
 * Prints a little counter at the bottom, until the value reaches a threshold, defined in [LEVEL_STEP_COUNT_STEP_THRESHOLD].
 */
@Composable
fun Level_16(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val stepCount = remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val stepCounter = remember { StepCounter(context) }

    // Start and stop step detection when composable enters or exits composition
    DisposableEffect(Unit) {
        stepCounter.start()
        onDispose {
            stepCounter.stop()
        }
    }

    // Collect step count from StepCounter's StateFlow
    LaunchedEffect(stepCounter) {
        launch {
            stepCounter.stepCount.collectLatest { steps ->
                withContext(Dispatchers.Main) { // Ensure UI updates happen on the main thread
                    stepCount.intValue = steps
                }
            }
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.level_16),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        // Display the step count in the UI
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            if (stepCount.intValue <= LEVEL_STEP_COUNT_STEP_THRESHOLD) Text(text = "${stepCount.intValue}")
        }

        if (!hasStepCounterSensor(context)) {
            Text(
                text = stringResource(id = R.string.warning_level06),
                style = MaterialTheme.typography.bodyLarge
            )
            UnlockLevel(
                labelResourceId = R.string.button,
                level = 16,
                modifier = Modifier,
                levelName = Screens.LightSensor.name,
                navController = navController
            )
        }

        // Only show level button when steps are greater than STEP_THRESHOLD
        if (stepCount.intValue > LEVEL_STEP_COUNT_STEP_THRESHOLD) {
            // Level button
            UnlockLevel(
                labelResourceId = R.string.button,
                level = 16,
                modifier = Modifier,
                levelName = Screens.LightSensor.name,
                navController = navController
            )
        }
    }
}

fun hasStepCounterSensor(context: Context): Boolean{
    return context.packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)
}

@Preview
@Composable
fun StartLevel16Preview() {
    ClickMeTheme {
        Level_16(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}