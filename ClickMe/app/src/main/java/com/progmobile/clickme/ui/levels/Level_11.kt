package com.progmobile.clickme.ui.levels

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
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
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
 */
@SuppressLint("ServiceCast")
@Composable
fun Level_11(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current;
    val initialDarkMode = rememberSaveable {
        configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
    val currentNightMode = remember (configuration) {
        configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Titre
        Text(
            text = stringResource(id = R.string.level_11),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        // Level Button
        if (currentNightMode != initialDarkMode) {
            UnlockLevel(
                labelResourceId = R.string.button,
                level = 11,
                modifier,
                levelName = Screens.Level_12.name,
                navController
            )
        }
    }
}

@Preview
@Composable
fun StartLevel11Preview() {
    ClickMeTheme {
        Level_11(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}