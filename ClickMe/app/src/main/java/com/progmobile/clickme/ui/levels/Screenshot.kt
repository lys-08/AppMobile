package com.progmobile.clickme.ui.levels

import android.annotation.SuppressLint
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
import com.progmobile.clickme.ui.UnlockLevel
import com.progmobile.clickme.ui.theme.ClickMeTheme

/**
 * Composable that displays the level where you have to take a screenshot or a picture.
 *
 * Use a ContextObserver to check if a picture was added to storage during the level.
 * If it is the case, it means a picture has been taken (screenshot or else) and [UnlockLevel]
 * show the button to the current level.
 *
 * Four images are placed to imitate a camera screen.
 */
@SuppressLint("RestrictedApi")
@Composable
fun Screenshot(
    idLevel: Int,
    nextLevel: String,
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

    Image(
        painter = painterResource(id = R.drawable.corner),
        contentDescription = "Right Bottom Corner",
        modifier = Modifier
            .wrapContentSize(Alignment.BottomEnd)
            .size(100.dp)
            .clipToBounds()
    )

    Image(
        painter = painterResource(id = R.drawable.corner),
        contentDescription = "Left Bottom Corner",
        modifier = Modifier
            .wrapContentSize(Alignment.BottomStart)
            .size(100.dp)
            .clipToBounds()
            .graphicsLayer(
                scaleX = -1f
            )
    )

    Image(
        painter = painterResource(id = R.drawable.corner),
        contentDescription = "Left Bottom Corner",
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .size(100.dp)
            .clipToBounds()
            .graphicsLayer(
                rotationZ = 180f,
            )
    )

    Image(
        painter = painterResource(id = R.drawable.corner),
        contentDescription = "Left Bottom Corner",
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
            .size(100.dp)
            .clipToBounds()
            .graphicsLayer(
                rotationZ = 180f,
                scaleX = -1f
            )
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.level_screenshot),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        if (isScreenshotTaken) {
            UnlockLevel(
                labelResourceId = R.string.button,
                level = idLevel,
                modifier,
                levelName = nextLevel,
                navController
            )
        }

    }
}


@Preview
@Composable
fun StartScreenshotPreview() {
    ClickMeTheme {
        Screenshot(
            idLevel = -1,
            nextLevel = Screens.HomePage.name,
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}
