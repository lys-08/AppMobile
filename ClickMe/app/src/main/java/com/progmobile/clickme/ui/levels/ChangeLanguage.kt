package com.progmobile.clickme.ui.levels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
 * Composable that displays the language level.
 * Change the device language (any language) to go to the next level.
 */
@Composable
fun ChangeLanguage(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val currentLocale = configuration.locales[0]// Get the language

    var previousLocale by rememberSaveable { mutableStateOf(currentLocale) }
    var isLanguageChanged by remember { mutableStateOf(false) }

    if (currentLocale != previousLocale) {
        isLanguageChanged = true
        previousLocale = currentLocale
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.level_15),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        // Level button
        if (isLanguageChanged) {
            UnlockLevel(
                labelResourceId = R.string.button,
                level = 15,
                modifier = Modifier.wrapContentSize(),
                levelName = Screens.StepCountingLevel.name,
                navController = navController
            )
        }
    }
}

@Preview
@Composable
fun StartChangeLanguagePreview() {
    ClickMeTheme {
        ChangeLanguage(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}