package com.progmobile.clickme


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.data.DataSource.levels
import com.progmobile.clickme.ui.ClickMeBottomBar
import com.progmobile.clickme.ui.HomePage
import com.progmobile.clickme.ui.levels.Level_01
import com.progmobile.clickme.ui.levels.Level_02
import com.progmobile.clickme.ui.levels.Level_03
import com.progmobile.clickme.ui.levels.Level_04
import com.progmobile.clickme.ui.levels.Level_05
import com.progmobile.clickme.ui.levels.Level_06
import com.progmobile.clickme.ui.levels.Level_07
import com.progmobile.clickme.ui.levels.Level_08
import com.progmobile.clickme.ui.levels.Level_09
import com.progmobile.clickme.ui.levels.Level_10
import com.progmobile.clickme.ui.levels.Level_11

/*
 * Enum class containing the pages of the app
 */
enum class Screens(@StringRes val title: Int) {
    HomePage(title = R.string.app_name),
    Level_01(title = R.string.level_01),
    Level_02(title = R.string.level_02),
    Level_03(title = R.string.level_03),
    Level_04(title = R.string.level_04),
    Level_05(title = R.string.level_05),
    Level_06(title = R.string.level_06),
    Level_07(title = R.string.level_07),
    Level_08(title = R.string.level_08),
    Level_09(title = R.string.level_09),
    Level_10(title = R.string.level_10),
    Level_11(title = R.string.level_11)
}

@Composable
fun ClickMeApp(
    navController: NavHostController = rememberNavController()
) {

    val levelHints = mapOf(
        // TODO : Transform this to list of strings in string.xml
        Screens.Level_01.name to listOf("Hint for Level 1", "Second hint for Level 1"),
        Screens.Level_02.name to listOf("Hint for Level 2", "Second hint for Level 2", "Third hint for Level 2"),
        Screens.Level_03.name to listOf("Hint for Level 3"),
        Screens.Level_07.name to listOf("I'm kind of tired", "I really lack energy")
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screens.valueOf(
        backStackEntry?.destination?.route?: Screens.HomePage.name
    )
    Scaffold(
        bottomBar = {
            ClickMeBottomBar(
                levelHints = levelHints,
                navController = navController,
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
                        R.string.level_06 -> Level_06(navController = navController, modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_medium)))
                        R.string.level_07 -> Level_07(navController = navController, modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_medium)))
                        R.string.level_08 -> Level_08(navController = navController, modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_medium)))
                        R.string.level_09 -> Level_09(navController = navController, modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_medium)))
                        R.string.level_10 -> Level_10(navController = navController, modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_medium)))
                        R.string.level_11 -> Level_11(navController = navController, modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_medium)))
                    }
                }
            }
        }
    }
}