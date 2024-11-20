package com.progmobile.clickme.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.progmobile.clickme.data.DataSource.LEVEL_NUMBERS
import com.progmobile.clickme.data.DataSource.levels


/**
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
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

        // Levels button
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(8.dp), // Ajoute un espace global autour de la grille
            horizontalArrangement = Arrangement.spacedBy(8.dp), // Espacement horizontal entre les items
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            //Enable buttons from unlocked levels
            items((0..MainActivity.instance?.currentLevelUnlocked!!).toList()) { i ->
                LevelButton(
                    labelResourceId = levels[i].first,
                    onClick = { navController.navigate(levels[i].second) }
                )
            }
            if (MainActivity.instance?.currentLevelUnlocked!! == 17) {
                item {
                    UnlockLevel(
                        labelResourceId = R.string.button,
                        level = 18,
                        //modifier = Modifier.wrapContentSize(),
                        levelName = Screens.Level_19.name,
                        navController = navController
                    )
                }
            }
            //Disable buttons from locked levels
            items((MainActivity.instance?.currentLevelUnlocked!! + 1..<LEVEL_NUMBERS).toList()) { i ->
                LevelButtonLocked(
                    onClick = { navController.navigate(levels[i].second) }
                )
            }


        }

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