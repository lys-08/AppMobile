package com.progmobile.clickme


import androidx.activity.addCallback
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.data.DataSource.levelsMap
import com.progmobile.clickme.ui.ClickMeBottomBar
import com.progmobile.clickme.ui.HomePage

/*
 * Enum class containing the pages of the app
 */
enum class Screens(@StringRes val title: Int) {
    HomePage(title = R.string.app_name),
    Charging(title = R.string.level_charging),
    Microphone(title = R.string.level_microphone),
    DarkMode(title = R.string.level_dark_mode),
    AirplaneMode(title = R.string.level_airplane_mode),
    LightSensor(title = R.string.level_light_sensor),
    ShutdownDevice(title = R.string.level_shutdown_device),
    Place10Finger(title = R.string.level_place_ten_fingers),
    Wait20s(title = R.string.level_place_ten_fingers),
    SimpleButton(title = R.string.level_simple_button),
    LongPressButton(title = R.string.level_long_press_button),
    DoubleButtons(title = R.string.level_double_buttons),
    Orientation(title = R.string.level_orientation),
    Screenshot(title = R.string.level_screenshot),
    LightTorch(title = R.string.level_light_torch),
    DropDownMenu(title = R.string.level_drop_down_menu),
    LostButton(title = R.string.level_lost_button),
    Labyrinth(title = R.string.level_labyrinth),
    MovingButton(title = R.string.level_moving_button),
    ChangeLanguage(title = R.string.level_change_language),
    StepCountingLevel(title = R.string.level_step_counting),
    ButtonInHomepage(title = R.string.level_button_in_home_page),
    ScrollToFindTheButton(title = R.string.level_scroll_to_find_button) // TODO : change string
}

/**
 * Application composable, calls the Home Page, setups the Bottom Bar and the routes for the levels
 * @param navController the navigation controller, could be passed from MainActivity but currently not used that way
 * @param mainActivityInstance the instance of MainActivity, passed from MainActivity to override the back button behaviour, to always go back to HomePage
 */
@Composable
fun ClickMeApp(
    navController: NavHostController = rememberNavController(),
    mainActivityInstance: MainActivity
) {
    // Override callback behaviour to always go back to HomePage
    mainActivityInstance.onBackPressedDispatcher.addCallback {
        // Handle the back button event
        navController.navigate(Screens.HomePage.name) {
            popUpTo(0) // Clear the back stack
        }
    }

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