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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.MainActivity
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
import com.progmobile.clickme.ui.UnlockLevel
import com.progmobile.clickme.ui.theme.ClickMeTheme
import kotlinx.coroutines.launch

/**
 * Composable that displays the language level.
 * Change the device language ( to any language) to go to the next level.
 */
var isLanguageChanged = false

@Composable
fun ChangeLanguage(
    idLevel: Int,
    nextLevel: String,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.level_change_language),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        val coroutineScope = rememberCoroutineScope()

        // Level button
        if (isLanguageChanged) {
            UnlockLevel(
                labelResourceId = R.string.button,
                level = idLevel,
                modifier = Modifier.wrapContentSize(),
                levelName = nextLevel,
                navController = navController,
                onUnlock = {
                    coroutineScope.launch {
                        isLanguageChanged(false)
                    }
                    navController.navigate(nextLevel)
                    if (MainActivity.instance?.currentLevelUnlocked!! < idLevel) {
                        MainActivity.instance?.increaseLevel()
                    }},
            )
        }
    }
}

// Change isLanguageChanged value (called in MainActivity)
fun isLanguageChanged(changed: Boolean) {
    isLanguageChanged = changed
}

@Preview
@Composable
fun StartChangeLanguagePreview() {
    ClickMeTheme {
        ChangeLanguage(
            idLevel = -1,
            nextLevel = Screens.HomePage.name,
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}