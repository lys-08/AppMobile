package com.progmobile.clickme.ui.levels

import android.content.ContentResolver
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
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

class ScreenshotObserver(private val contentResolver: ContentResolver) {
    private val handler = Handler(Looper.getMainLooper())
    private var screenshotTaken = mutableStateOf(false)

    private val observer = object : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)

            // Effectuer l'interrogation sur un thread secondaire
            Thread {
                val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)

                cursor?.use {
                    if (it.moveToLast()) {
                        val columnIndex = it.getColumnIndex(MediaStore.Images.Media.DATA)
                        val filePath = it.getString(columnIndex)

                        if (filePath.contains("/Screenshots/")) { // Vérifiez si c'est dans le dossier Screenshots
                            screenshotTaken.value = true
                        }
                    }
                }
            }.start()
        }
    }

    fun registerObserver() {
        contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            observer
        )
    }

    fun unregisterObserver() {
        contentResolver.unregisterContentObserver(observer)
    }

    val isScreenshotTaken: State<Boolean>
        get() = screenshotTaken
}


/**
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
 */
@Composable
fun Level_05(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val screenshotObserver = remember { ScreenshotObserver(context.contentResolver) }

    // État pour suivre si une capture d'écran a été prise
    var isScreenshotTaken by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        screenshotObserver.registerObserver() // Enregistre l'observateur
        // Observer l'état de capture d'écran
        isScreenshotTaken = screenshotObserver.isScreenshotTaken.value // Met à jour l'état
        onDispose {
            screenshotObserver.unregisterObserver() // Désinscrit l'observateur
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.level_05),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        if (isScreenshotTaken) {
            UnlockLevel(
                labelResourceId = R.string.button,
                level = 5,
                modifier,
                levelName = Screens.Level_06.name,
                navController
            )
        }

    }
}


@Preview
@Composable
fun StartLevel05Preview() {
    MaterialTheme {
        Level_05(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}
