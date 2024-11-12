package com.progmobile.clickme.ui.levels

import android.annotation.SuppressLint
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import android.Manifest
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
import com.progmobile.clickme.ui.UnlockLevel

/**
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
 */
@SuppressLint("RestrictedApi")
@Composable
fun Level_05(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val handler = Handler(Looper.getMainLooper())
    var isScreenshotTaken by remember {
        mutableStateOf(false)
    }

    val registerObserver = {
        val observer = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean, uri: Uri?) {
                super.onChange(selfChange, uri)
                isScreenshotTaken = true
            }
        }
        context.contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            observer
        )
    }

    LaunchedEffect(Unit) {
        ActivityCompat.requestPermissions(
            (context as ComponentActivity),
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
            ),
            100
        )
        registerObserver()
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
