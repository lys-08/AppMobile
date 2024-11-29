package com.progmobile.clickme


import androidx.activity.addCallback
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.data.DataSource.levelsMap
import com.progmobile.clickme.ui.ClickMeBottomBar
import com.progmobile.clickme.ui.HomePage

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
    Level_11(title = R.string.level_11),
    Level_12(title = R.string.level_12),
    Level_13(title = R.string.level_13),
    Level_14(title = R.string.level_14),
    Level_15(title = R.string.level_15),
    Level_16(title = R.string.level_16),
    Level_17(title = R.string.level_17),
    Level_18(title = R.string.level_18),
    Level_19(title = R.string.level_19),
    Level_20(title = R.string.level_20),
    Level_21(title = R.string.level_20)
}

/**
 * Application composable, calls the Home Page, setups the Bottom Bar and the routes for the levels
 * @param navController the navigation controller, could be passed from MainActivity but currently not used that way
 * @param mainActivityInstance the instance of MainActivity, passed from MainActivity to override the back button behaviour, to always go back to HomePage
 * @param permissionsDenied state to check if at least one permission have been denied, currently unused
 */
@Composable
fun ClickMeApp(
    permissionsDenied : MutableState<Boolean>,
    navController: NavHostController = rememberNavController(),
    mainActivityInstance: MainActivity
) {
    // ================= PERMISSION =================
    /*var showDialog by remember { mutableStateOf(false) }

    // An Alert message is print if at least one permission have been denied
    // TODO : fix
    LaunchedEffect(!permissionsDenied.value) {
        if (!permissionsDenied.value) {
            showDialog = true
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Permission denied") },
            text = { Text("Some permission have been denied. WARNING : you may nit complete some level, please accept the permission.") },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }*/
    // =============== END PERMISSION ===============

    // Override callback behaviour to always go back to HomePage
    mainActivityInstance.onBackPressedDispatcher.addCallback() {
        // Handle the back button event
        navController.navigate(Screens.HomePage.name) {
            popUpTo(0) // Clear the back stack
        }
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screens.valueOf(
        backStackEntry?.destination?.route?: Screens.HomePage.name
    )
    Scaffold(
        bottomBar = {
            ClickMeBottomBar(
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

            // Setup Levels Nav Graph
            levelsMap.forEach { (levelName, levelComposable) ->
                composable(route = levelName) {
                    levelComposable(navController, Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_medium)))()
                }
            }
        }
    }
}