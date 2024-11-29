package com.progmobile.clickme.ui.levels

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
import com.progmobile.clickme.ui.UnlockLevel
import com.progmobile.clickme.ui.theme.ClickMeTheme


/**
 * Composable that displays a labyrinth.
 * Solve the labyrinth using the device inclination.
 */
@Composable
fun Level_13(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var playerPosition by remember { mutableStateOf(Pair(10, 5)) }
    val maze = listOf(
        listOf(1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1),
        listOf(1, 2, 0, 0, 0, 0, 1, 0, 0, 0, 1),
        listOf(1, 1, 1, 1, 1, 0, 1, 0, 2, 0, 1),
        listOf(1, 0, 0, 2, 1, 0, 0, 0, 0, 0, 1),
        listOf(1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1),
        listOf(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1),
        listOf(1, 0, 0, 0, 1, 1, 1, 2, 1, 0, 1),
        listOf(1, 0, 2, 0, 1, 0, 0, 0, 1, 0, 1),
        listOf(1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1),
        listOf(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1),
        listOf(1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1)
    )

    // Move player based on direction
    val movePlayer: (Int, Int) -> Unit = { rowDelta, colDelta ->
        val newRow = playerPosition.first + rowDelta
        val newCol = playerPosition.second + colDelta
        if (maze.getOrNull(newRow)?.getOrNull(newCol) == 0) {
            playerPosition = newRow to newCol
        }
        else if (maze.getOrNull(newRow)?.getOrNull(newCol) == 2) {
            playerPosition = Pair(10,5)
        }
    }

    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    // Déclaration de la variable d'accéléromètre
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // Variables pour stocker les rotations
    var pitch by remember { mutableStateOf(0f) }
    var roll by remember { mutableStateOf(0f) }
    // Sensor management

    DisposableEffect(Unit) {
        // Créer un Listener pour l'accéléromètre
        val accelerometerListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null || event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

                // Récupérer les valeurs de l'accéléromètre
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                // Calculer les angles d'inclinaison (pitch et roll)
                pitch = Math.toDegrees(Math.atan2(y.toDouble(), z.toDouble())).toFloat()
                roll = Math.toDegrees(Math.atan2(x.toDouble(), z.toDouble())).toFloat()

                // Interprétation de l'orientation
                when {
                    pitch > 30 ->  {
                        Log.d("Orientation", "Inclinaison arrière")
                        movePlayer(1,0)
                    }
                    pitch < -30 -> {
                        Log.d("Orientation", "Inclinaison avant")
                        movePlayer(-1,0)
                    }
                    roll > 30 -> {
                        Log.d("Orientation", "Inclinaison à gauche")
                        movePlayer(0,-1)
                    }
                    roll < -30 -> {
                        Log.d("Orientation", "Inclinaison à droite")
                        movePlayer(0,1)
                    }
                    else -> Log.d("Orientation", "Tablette droite")
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // Pas nécessaire dans ce cas, mais obligatoire de l'implémenter
            }
        }

        // Enregistrer le listener pour écouter les changements de l'accéléromètre
        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_UI)

        onDispose { sensorManager.unregisterListener(accelerometerListener) }


    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.level_13),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        // Canvas pour le labyrinthe
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            val cellSize = size.width / maze[0].size

            // Dessiner le labyrinthe
            maze.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { colIndex, cell ->
                    val color =
                        if (cell == 1)
                            Color.Black
                        else if (cell == 2)
                            Color.Gray
                        else if (rowIndex == 0 && colIndex == 5)
                            Color.Green
                        else Color.White
                    drawRect(
                        color = color,
                        topLeft = Offset(colIndex * cellSize, rowIndex * cellSize),
                        size = Size(cellSize, cellSize)
                    )
                }
            }

            // Dessiner le joueur
            drawCircle(
                color = Color.Red,
                radius = cellSize / 4,
                center = Offset(
                    playerPosition.second * cellSize + cellSize / 2,
                    playerPosition.first * cellSize + cellSize / 2
                )
            )
        }

        Log.d("MyActivity", "Player : $playerPosition")

        // Level button
        if (playerPosition.first == 0 && playerPosition.second == 5){
            Log.d("MyActivity", "Here")
            UnlockLevel(
                labelResourceId = R.string.button,
                level = 13,
                modifier,
                levelName = Screens.Level_14.name,
                navController
            )
        }
    }
}

@Preview
@Composable
fun StartLevel13Preview() {
    ClickMeTheme {
        Level_13(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}