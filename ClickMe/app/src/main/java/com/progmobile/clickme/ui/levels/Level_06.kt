package com.progmobile.clickme.ui.levels

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.progmobile.clickme.ui.UnlockLevel
import android.content.pm.PackageManager

// Classe TorchManager pour gérer la lampe torche
class TorchManager(context: Context) {
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var torchCallback: ((Boolean) -> Unit)? = null

    fun registerTorchCallback(callback: (Boolean) -> Unit) {
        torchCallback = callback
        cameraManager.registerTorchCallback(torchListener, null)
    }

    fun unregisterTorchCallback() {
        cameraManager.unregisterTorchCallback(torchListener)
        torchCallback = null
    }

    private val torchListener = object : CameraManager.TorchCallback() {
        override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
            torchCallback?.invoke(enabled)
        }
    }
}

// Composable Level_06

/**
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
 */

@Composable
fun Level_06(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val torchManager = remember { TorchManager(context) }

    var isTorchOn by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        torchManager.registerTorchCallback { isTorchOn = it }
        onDispose {
            torchManager.unregisterTorchCallback()
        }
    }

    val backgroundColor = if (isTorchOn) {
        MaterialTheme.colorScheme.primary // Couleur personnalisée si la torche est allumée
    } else {
        MaterialTheme.colorScheme.background // Couleur par défaut
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.level_06),
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )


            if (isTorchOn) {
                UnlockLevel(
                    labelResourceId = R.string.button,
                    level = 6,
                    modifier,
                    levelName = Screens.Level_07.name,
                    navController
                )
            }

            if (!hasFlashlight(context)) {
                Text(
                    text = stringResource(id = R.string.warning_level06),
                    style = MaterialTheme.typography.bodyLarge
                )
                UnlockLevel(
                    labelResourceId = R.string.button,
                    level = 6,
                    modifier,
                    levelName = Screens.Level_07.name,
                    navController
                )

            }

        }
    }
}

fun hasFlashlight(context: Context): Boolean {
    return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
}

@Preview
@Composable
fun StartLevel06Preview() {
    MaterialTheme {
        Level_06(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}
