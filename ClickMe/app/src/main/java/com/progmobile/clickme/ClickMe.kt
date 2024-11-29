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
    Charging(title = R.string.level_07),
    Microphone(title = R.string.level_08),
    DarkMode(title = R.string.level_11),
    AirplaneMode(title = R.string.level_12),
    LightSensor(title = R.string.level_17),
    ShutdownDevice(title = R.string.level_19),
    Place10Finger(title = R.string.level_20),
    Wait20s(title = R.string.level_20),
    SimpleButton(title = R.string.level_simple_button),
    LongPressButton(title = R.string.level_02),
    DoubleButtons(title = R.string.level_03),
    Orientation(title = R.string.level_04),
    Screenshot(title = R.string.level_05),
    LightTorch(title = R.string.level_06),
    DropDownMenu(title = R.string.level_09),
    LostButton(title = R.string.level_10),
    Labyrinth(title = R.string.level_13),
    MovingButton(title = R.string.level_14),
    ChangeLanguage(title = R.string.level_15),
    StepCountingLevel(title = R.string.level_16),
    ButtonInHomepage(title = R.string.level_18),
    ScrollToFindTheButton(title = R.string.level_18) // TODO : change string
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