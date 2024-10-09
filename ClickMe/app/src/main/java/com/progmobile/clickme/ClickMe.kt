package com.progmobile.clickme


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.data.DataSource.levels
import com.progmobile.clickme.ui.HintIconButton
import com.progmobile.clickme.ui.HomePage
import com.progmobile.clickme.ui.LevelButton
import com.progmobile.clickme.ui.ParameterIconButton
import com.progmobile.clickme.ui.levels.Level_01
import com.progmobile.clickme.ui.levels.Level_02
import com.progmobile.clickme.ui.levels.Level_03
import com.progmobile.clickme.ui.levels.Level_04
import com.progmobile.clickme.ui.levels.Level_05

/*
 * Enum class containing the pages of the app
 */
enum class Screens(@StringRes val title: Int) {
    HomePage(title = R.string.app_name),
    Level_01(title = R.string.level_01),
    Level_02(title = R.string.level_02),
    Level_03(title = R.string.level_03),
    Level_04(title = R.string.level_04),
    Level_05(title = R.string.level_05)
}

@Composable
fun ClickMeBottomBar(
    levelHints: Map<Int, List<String>>,
    currentLevel: Int,
    modifier: Modifier = Modifier
) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left-aligned: ParameterIconButton
            ParameterIconButton()

            // Right-aligned: HintIconButton
            HintIconButton(
                listOfHints = levelHints[currentLevel] ?: emptyList() // Pass hints for the current level
            )
        }
    }
}


@Composable
fun ClickMeApp(
    navController: NavHostController = rememberNavController()
) {
    // State to hold the current level
    var currentLevel by remember { mutableStateOf(1) }

    val levelHints = mapOf(
        // TODO : Transform this to list of strings in string.xml
        1 to listOf("Hint for Level 1", "Second hint for Level 1", "Third hint for Level 1"),
        2 to listOf("Hint for Level 2", "Second hint for Level 2", "Third hint for Level 2"),
        3 to listOf("Hint for Level 3", "Second hint for Level 3", "Third hint for Level 3")
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screens.valueOf(
        backStackEntry?.destination?.route?: Screens.HomePage.name
    )
    Scaffold(
        bottomBar = {
            ClickMeBottomBar(
                levelHints = levelHints,
                currentLevel = currentLevel,
                modifier = Modifier
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screens.HomePage.name,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable(route = Screens.HomePage.name) {
                HomePage(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding((dimensionResource(id = R.dimen.padding_medium)))
                )
            }
            levels.forEach { level ->
                composable(route = level.second) {
                    when (level.first) {
                        R.string.level_01 -> Level_01(navController = navController, modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_medium)))
                        R.string.level_02 -> Level_02(navController = navController, modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_medium)))
                        R.string.level_03 -> Level_03(navController = navController, modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_medium)))
                        R.string.level_04 -> Level_04(navController = navController, modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_medium)))
                        R.string.level_05 -> Level_05(navController = navController, modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_medium)))
                    }
                }
            }
        }
    }
}