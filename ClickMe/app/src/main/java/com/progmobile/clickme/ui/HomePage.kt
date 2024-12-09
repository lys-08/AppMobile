package com.progmobile.clickme.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
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
import com.progmobile.clickme.data.DataSource
import com.progmobile.clickme.data.DataSource.LEVEL_NUMBERS
import com.progmobile.clickme.data.DataSource.levels
import com.progmobile.clickme.data.DataSource.levelsMap


/**
 * Composable that allows the user to select the level he/she wants to play
 */
@Composable
fun HomePage(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        DetectLanguageChange()

        // Levels button
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth()
                                .fillMaxHeight(),
            contentPadding = PaddingValues(8.dp), // Ajoute un espace global autour de la grille
            horizontalArrangement = Arrangement.spacedBy(8.dp), // Space between items
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
              val levelList = levelsMap.keys.toList();

              //Enable buttons from unlocked levels
              items((0..MainActivity.instance?.currentLevelUnlocked!!).toList()) { i ->
                  LevelButton(
                      labelResourceId = levels.get(levelList[i]) ?: R.string.error,//levels[i].first,
                      onClick = { navController.navigate(levelList[i]) }, //levels[i].second
                      // Concatenate level number and the dash
                      prefix = "${i + 1}-",
                  )
              }

            if (MainActivity.instance?.currentLevelUnlocked!! == 8) {
                item {
                    UnlockLevel(
                        labelResourceId = R.string.button,
                        level = 9,
                        //modifier = Modifier.wrapContentSize(),
                        levelName = Screens.LightTorch.name,
                        navController = navController
                    )
                }
            }

            //Disable buttons from locked levels
              items((MainActivity.instance?.currentLevelUnlocked!! + 1..<LEVEL_NUMBERS).toList()) { i ->
                  LevelButtonLocked(
                      onClick = { navController.navigate(levelList[i]) }
                  )
              }
        }

    }
}

@Composable
fun DetectLanguageChange() {
    val configuration = LocalConfiguration.current
    val currentLocale = configuration.locales[0].toString() // Obtenez la langue actuelle

    LaunchedEffect(currentLocale) {
        DataSource.updateLocale(currentLocale)
    }
}


@Preview
@Composable
fun StartHomePagePreview() {
    MaterialTheme {
        HomePage(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}