package com.progmobile.clickme.ui.levels

import android.content.Intent
import android.content.IntentFilter
import android.media.MediaRecorder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File


/**
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
 */
@Composable
fun Level_08(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var amplitude by remember { mutableIntStateOf(0) }
    var isMonitoring by remember { mutableStateOf(false) }
    var mediaRecorder: MediaRecorder? by remember { mutableStateOf(null) }
    var timeCount by remember { mutableIntStateOf(0) }

    val outputFile = File.createTempFile("temp_record", ".3gp").absolutePath

    // =========== Monitoring Functions ===========

    fun startMonitoring() {
        if (mediaRecorder == null) {
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setOutputFile(outputFile)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                prepare()
                start()
            }

            // Start the coroutine
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    while (isMonitoring) {
                        amplitude = mediaRecorder?.maxAmplitude ?: 0
                        if (amplitude > 30000) {
                            timeCount += 1
                            // Stop the recording if the Amplitude above 30 000 during 3seconds
                            if (timeCount >= 30) {
                                isMonitoring = false
                            }
                        } else {
                            timeCount = 0
                        }
                        delay(100)
                    }
                } finally { // Free the resources
                    mediaRecorder?.apply {
                        stop()
                        release()
                    }
                    mediaRecorder = null
                }
            }
        }
    }

    // =================== End ===================

    LaunchedEffect(Unit) {
        isMonitoring = true
        startMonitoring()
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.level_08),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )
        
        // Level button
        if (!isMonitoring) {
            UnlockLevel(
                labelResourceId = R.string.button,
                level = 8,
                modifier,
                levelName = Screens.Level_09.name,
                navController
            )
        }
    }
}


@Preview
@Composable
fun StartLevel08Preview() {
    MaterialTheme {

        Level_08(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}