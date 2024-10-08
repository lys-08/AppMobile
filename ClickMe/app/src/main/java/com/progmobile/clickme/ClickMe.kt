package com.progmobile.clickme


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.data.DataSource.levels
import com.progmobile.clickme.ui.HomePage
import com.progmobile.clickme.ui.LevelButton
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
fun ClickMeApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screens.valueOf(
        backStackEntry?.destination?.route?: Screens.HomePage.name
    )
    Scaffold() {
            innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screens.HomePage.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screens.HomePage.name) {
                HomePage(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding((dimensionResource(id = R.dimen.padding_medium)))
                )
            }
            composable(route = Screens.Level_01.name) {
                Level_01(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding((dimensionResource(id = R.dimen.padding_medium)))
                )
            }
            composable(route = Screens.Level_02.name) {
                Level_02(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding((dimensionResource(id = R.dimen.padding_medium)))
                )
            }
            composable(route = Screens.Level_03.name) {
                Level_03(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding((dimensionResource(id = R.dimen.padding_medium)))
                )
            }
            composable(route = Screens.Level_04.name) {
                Level_04(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding((dimensionResource(id = R.dimen.padding_medium)))
                )
            }
            composable(route = Screens.Level_05.name) {
                Level_05(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding((dimensionResource(id = R.dimen.padding_medium)))
                )
            }
        }
    }
}