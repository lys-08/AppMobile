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
import kotlin.math.log
import kotlin.math.sqrt

class OrientationSensor(context: Context, private val onMove: (Int, Int) -> Unit) : SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)  // Utilisation du gyroscope

    // Variables pour le suivi de la rotation
    private var rotationX = 0f
    private var rotationY = 0f
    private var rotationZ = 0f

    init {
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI)
    }

    // Logique pour gérer les changements dans les valeurs du gyroscope
    override fun onSensorChanged(event: SensorEvent?) {
        Log.d("MyActivity", "here10")
        Log.d("MyActivity", "Event : $event")
        if (event == null || event.sensor.type != Sensor.TYPE_GYROSCOPE) return
        Log.d("MyActivity", "here2")

        // Les valeurs du gyroscope (degré de rotation sur les axes X, Y, Z)
        val deltaX = event.values[0]
        val deltaY = event.values[1]
        val deltaZ = event.values[2]

        // Mettez à jour les valeurs de rotation cumulées
        rotationX += deltaX
        rotationY += deltaY
        rotationZ += deltaZ

        // Utilisez les rotations pour déterminer les mouvements (par exemple, dans les 4 directions)
        // Vous pouvez définir des seuils pour déterminer quand le joueur se déplace
        when {
            rotationX > 1 -> onMove(1, 0)  // Déplacer vers le bas
            rotationX < -1 -> onMove(-1, 0) // Déplacer vers le haut
            rotationY > 1 -> onMove(0, 1)   // Déplacer vers la droite
            rotationY < -1 -> onMove(0, -1) // Déplacer vers la gauche
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    fun stop() {
        // Désabonnez-vous du gyroscope lorsque vous n'en avez plus besoin
        sensorManager.unregisterListener(this)
    }
}





/**
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
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
        listOf(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1),
        listOf(1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1),
        listOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1),
        listOf(1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1),
        listOf(1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1),
        listOf(1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1),
        listOf(1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1),
        listOf(1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1),
        listOf(1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1),
        listOf(1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1)
    )

    Log.d("MyActivity", "Here!!")

    // Move player based on direction
    val movePlayer: (Int, Int) -> Unit = { rowDelta, colDelta ->
        val newRow = playerPosition.first + rowDelta
        val newCol = playerPosition.second + colDelta
        Log.d("MyActivity", "Here2!!")
        if (maze.getOrNull(newRow)?.getOrNull(newCol) == 0) {
            playerPosition = newRow to newCol
        }
    }

    // Sensor management
    val sensorManager = remember { OrientationSensor(context, onMove = movePlayer) }

    DisposableEffect(Unit) {
        onDispose { sensorManager.stop() }
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
                    val color = if (cell == 1) Color.Black else Color.White
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

        // Level button
        /*UnlockLevel(
            labelResourceId = R.string.button,
            level = 13,
            modifier,
            levelName = Screens.Level_14.name,
            navController
        )*/
    }
}

@Preview
@Composable
fun StartLevel13Preview() {
    MaterialTheme {

        Level_13(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}