package com.progmobile.clickme.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
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
import kotlinx.coroutines.launch

var isLastPage8 = false

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

        val coroutineScope = rememberCoroutineScope()

        //Put logo
        Image(
            painter = painterResource(id = R.drawable.clickmelogo),
            contentDescription = "Click Me logo and title",
            modifier = Modifier
        )

        // Levels button
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentPadding = PaddingValues(35.dp), // Space outside the grid
            horizontalArrangement = Arrangement.spacedBy(20.dp), // Space between items
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
              val levelList = levelsMap.keys.toList();

              //Enable buttons from unlocked levels
              items((0..MainActivity.instance?.currentLevelUnlocked!!).toList()) { i ->
                  LevelButton(
                      labelResourceId = levels.get(levelList[i]) ?: R.string.error,//levels[i].first,
                      onClick = { navController.navigate(levelList[i]) }, //levels[i].second
                      // Concatenate level number and the dash
                      prefix = "${i + 1}-",
                      modifier = Modifier
                          .height(80.dp)
                          .fillMaxHeight(1f)
                  )
              }
            //Show button from level 08 (button in homepage)
            if (MainActivity.instance?.currentLevelUnlocked!! == 8 || isLastPage8) {
                item {
                    UnlockLevel(
                        labelResourceId = R.string.button,
                        level = 9,
                        levelName = Screens.LightTorch.name,
                        navController = navController,
                        onUnlock = {
                            coroutineScope.launch {
                                isLastPage8 = false
                            }
                            navController.navigate(Screens.LightTorch.name)
                            if (MainActivity.instance?.currentLevelUnlocked!! < 9) {
                                MainActivity.instance?.increaseLevel()
                            }},
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
fun isLastPage(isLastPage: Boolean) {
    isLastPage8 = isLastPage
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