package com.progmobile.clickme


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
    Level_11(title = R.string.level_11)
}

@Composable
fun ClickMeApp(
    permissionsDenied : MutableState<Boolean>,
    navController: NavHostController = rememberNavController()
) {
    // ================= PERMISSION =================
    var showDialog by remember { mutableStateOf(false) }

    // An Alert message is print if at least one permission have been denied
    LaunchedEffect(permissionsDenied.value) {
        if (permissionsDenied.value) {
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
    }
    // =============== END PERMISSION ===============

    val levelHints = mapOf(
        // TODO : Transform this to list of strings in string.xml
        Screens.Level_01.name to listOf("Hint for Level 1", "Second hint for Level 1"),
        Screens.Level_02.name to listOf("Hint for Level 2", "Second hint for Level 2", "Third hint for Level 2"),
        Screens.Level_03.name to listOf("Hint for Level 3"),
        Screens.Level_07.name to listOf("I'm kind of tired", "I really lack energy"),
        Screens.Level_08.name to listOf(
            "I whisper in the fields and howl in the storms. They follow me, but I have no path. Who am I?",
            "Listen to the colors of the wind",
            "Just breathing can move mountains"),
        Screens.Level_11.name to listOf(
            "Seek the shadow to find the light, seek the light to find the shadow",
            "Sometimes you have to dive into the depths to see the light"
            )
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

            // Setup Levels Nav Graph
            levelsMap.forEach { (levelName, levelComposable) ->
                composable(route = levelName) {
                    levelComposable(navController, Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_medium)))()
                }
            }
        }
    }
}