package com.progmobile.clickme.ui.levels

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
import com.progmobile.clickme.ui.UnlockLevel
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.material3.Text
import com.progmobile.clickme.ui.theme.ClickMeTheme


/**
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
 */
@Composable
fun Level_04(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            RotatedText(screenWidth)
        }
        else {
            NormalText(screenWidth)
            UnlockLevel(
                labelResourceId = R.string.button,
                level = 4,
                modifier = Modifier,
                levelName = Screens.Level_05.name,
                navController = navController
            )
        }
    }
}

@Composable
fun RotatedText(
    screenWidth: Int
) {
    val textSize = (screenWidth / 2).sp
    Text(
        text = stringResource(id = R.string.level_04),
        style = MaterialTheme.typography.displayLarge,
        fontSize = textSize,
        modifier = Modifier,
    )
}

@Composable
fun NormalText(
    screenWidth: Int
) {
    val textSize = (screenWidth / 10).sp
    Text(
        text = stringResource(id = R.string.level_04),
        style = MaterialTheme.typography.displayLarge,
        fontSize = textSize,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        textAlign = TextAlign.Center
    )
}


@Preview
@Composable
fun StartLevel04Preview() {
    ClickMeTheme {
        Level_04(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}