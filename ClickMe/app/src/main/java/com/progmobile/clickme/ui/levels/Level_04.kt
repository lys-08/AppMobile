package com.progmobile.clickme.ui.levels

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
import com.progmobile.clickme.data.DataSource.currentLevel
import com.progmobile.clickme.ui.LevelButton
import com.progmobile.clickme.ui.UnlockLevel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue




/**
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
 */
@Composable
fun Level_04(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    var isImageVisible by remember { mutableStateOf(true) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.level_04),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        if (isImageVisible) {
            Image(
                painter = painterResource(id = R.drawable.screenshot_frame),
                contentDescription = "Screenshot frame",
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(bottom = 200.dp)
            )
        }
        //ShowButton(modifier = modifier, navController = navController)
    }
}


@Composable
fun ShowButton(
    modifier: Modifier,
    navController: NavHostController) {
    // Level button
    UnlockLevel(
        labelResourceId = R.string.button,
        level = 4,
        modifier,
        levelName = Screens.Level_05.name,
        navController
    )
}

@Preview
@Composable
fun StartLevel04Preview() {
    MaterialTheme {
        Level_04(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}