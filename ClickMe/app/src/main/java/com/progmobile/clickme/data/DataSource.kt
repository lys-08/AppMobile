package com.progmobile.clickme.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
import com.progmobile.clickme.ui.levels.Charging
import com.progmobile.clickme.ui.levels.Microphone
import com.progmobile.clickme.ui.levels.DarkMode
import com.progmobile.clickme.ui.levels.AirplaneMode
import com.progmobile.clickme.ui.levels.LightSensor
import com.progmobile.clickme.ui.levels.ShutdownDevice
import com.progmobile.clickme.ui.levels.Place10Finger
import com.progmobile.clickme.ui.levels.Wait20s
import com.progmobile.clickme.ui.levels.SimpleButton
import com.progmobile.clickme.ui.levels.LongPressButton
import com.progmobile.clickme.ui.levels.DoubleButtons
import com.progmobile.clickme.ui.levels.Orientation
import com.progmobile.clickme.ui.levels.Screenshot
import com.progmobile.clickme.ui.levels.LightTorch
import com.progmobile.clickme.ui.levels.DropDownMenu
import com.progmobile.clickme.ui.levels.LostButton
import com.progmobile.clickme.ui.levels.Labyrinth
import com.progmobile.clickme.ui.levels.MovingButton
import com.progmobile.clickme.ui.levels.ChangeLanguage
import com.progmobile.clickme.ui.levels.ButtonInHomepage
import com.progmobile.clickme.ui.levels.ScroolToFindTheButton
import com.progmobile.clickme.ui.theme.CustomTheme

object DataSource {
    // State of the application
    var isAppInForeground = mutableStateOf(false)

    // Max number of levels to display
    const val LEVEL_NUMBERS = 21
    const val STARTING_LEVEL = 21
    const val MUSIC_DEFAULT = true
    const val SOUND_DEFAULT = true
    const val HINT_TEXT_SIZE = 20f
    const val IN_BOTTOM_BAR_BUTTONS_SIZE_RELATIVE_TO_SCREEN_WIDTH = 0.2f
    const val IN_PARAMETER_BUTTONS_SPACE_RELATIVE_TO_SCREEN_WIDTH = 0.1f

    // Level-specific constants
    const val LEVEL_PATIENCE_LONG_PRESS_DURATION = 3000L
    const val LEVEL_STEP_COUNT_STEP_THRESHOLD = 40

    val levels = mapOf(
        Screens.SimpleButton.name to R.string.level_01,
        Screens.LongPressButton.name to R.string.level_02,
        Screens.DoubleButtons.name to R.string.level_03,
        Screens.Orientation.name to R.string.level_04,
        Screens.Screenshot.name to R.string.level_05,
        Screens.LightTorch.name to R.string.level_06,
        Screens.Charging.name to R.string.level_07,
        Screens.Microphone.name to R.string.level_08,
        Screens.DropDownMenu.name to R.string.level_09,
        Screens.LostButton.name to R.string.level_10,
        Screens.DarkMode.name to R.string.level_11,
        Screens.AirplaneMode.name to R.string.level_12,
        Screens.Labyrinth.name to R.string.level_13,
        Screens.MovingButton.name to R.string.level_14,
        Screens.ChangeLanguage.name to R.string.level_15,
        Screens.StepCountingLevel.name to R.string.level_16,
        Screens.LightSensor.name to R.string.level_17,
        Screens.ButtonInHomepage.name to R.string.level_18,
        Screens.ShutdownDevice.name to R.string.level_19,
        Screens.Place10Finger.name to R.string.level_20,
        Screens.Wait20s.name to R.string.level_21,
        Screens.ScrollToFindTheButton.name to R.string.level_21 // TODO: Change string
    )

    val levelsMap: Map<String, (NavHostController, Modifier) -> @Composable () -> Unit> = mapOf(
       Screens.SimpleButton.name to {navController, modifier -> { SimpleButton(1, Screens.DoubleButtons.name, navController, modifier) } },
        Screens.DoubleButtons.name to { navController, modifier -> { DoubleButtons(2, Screens.LongPressButton.name, navController, modifier) } },
        Screens.LongPressButton.name to { navController, modifier -> { LongPressButton(3, Screens.Orientation.name, navController, modifier) } },
        Screens.Orientation.name to { navController, modifier -> { Orientation(4, Screens.AirplaneMode.name, navController, modifier) } },
        Screens.AirplaneMode.name to { navController, modifier -> { AirplaneMode(5, Screens.DropDownMenu.name, navController, modifier) } },
        Screens.DropDownMenu.name to { navController, modifier -> { DropDownMenu(/* idLevel = 6*/ Screens.Place10Finger.name, navController, modifier) } },
        Screens.Place10Finger.name to { navController, modifier -> { Place10Finger(7, Screens.ScrollToFindTheButton.name, navController, modifier) } },
        Screens.ScrollToFindTheButton.name to { navController, modifier -> { ScroolToFindTheButton(8, Screens.LostButton.name, navController, modifier) } },
        Screens.LostButton.name to { navController, modifier -> { LostButton(/* idLevel = 9*/ modifier) } },
        Screens.LightTorch.name to { navController, modifier -> { CustomTheme{LightTorch(10, Screens.Charging.name, navController, modifier)} } },
        Screens.Charging.name to { navController, modifier -> { Charging(11, Screens.ChangeLanguage.name, navController, modifier) } },
        Screens.ChangeLanguage.name to { navController, modifier -> { ChangeLanguage(12, Screens.DarkMode.name, navController, modifier) } },
        Screens.DarkMode.name to { navController, modifier -> { DarkMode(13, Screens.MovingButton.name, navController, modifier) } },
        Screens.MovingButton.name to { navController, modifier -> { MovingButton(14, Screens.Screenshot.name, navController, modifier) } },
        Screens.Screenshot.name to { navController, modifier -> { Screenshot(15, Screens.StepCountingLevel.name, navController, modifier) } },
        Screens.StepCountingLevel.name to { navController, modifier -> { Screenshot(16, Screens.ButtonInHomepage.name, navController, modifier) } },
        Screens.ButtonInHomepage.name to { navController, modifier -> { ButtonInHomepage(/* idLevel = 17*/ modifier) } },
        Screens.Microphone.name to { navController, modifier -> { Microphone(18, Screens.Labyrinth.name, navController, modifier) } },
        Screens.Labyrinth.name to { navController, modifier -> { Labyrinth(19, Screens.LightSensor.name, navController, modifier) } },
        Screens.LightSensor.name to { navController, modifier -> { LightSensor(20, Screens.ShutdownDevice.name, navController, modifier) } },
        Screens.ShutdownDevice.name to { navController, modifier -> { ShutdownDevice(21, Screens.Wait20s.name, navController, modifier) } },
        Screens.Wait20s.name to { navController, modifier -> { Wait20s(/* idLevel = 22*/ Screens.HomePage.name, navController, modifier) } },
    )

    val levelHints = mapOf(
        // map three hints to each level
        Screens.SimpleButton.name to listOf(
            R.string.hint_01_1,
            R.string.hint_01_2,
            R.string.hint_01_3),
        Screens.LongPressButton.name to listOf(
            R.string.hint_02_1,
            R.string.hint_02_3,
            R.string.hint_02_3),
        Screens.DoubleButtons.name to listOf(
            R.string.hint_03_1,
            R.string.hint_03_2,
            R.string.hint_03_3),
        Screens.Orientation.name to listOf(
            R.string.hint_04_1,
            R.string.hint_04_2,
            R.string.hint_04_3),
        Screens.Screenshot.name to listOf(
            R.string.hint_05_1,
            R.string.hint_05_2,
            R.string.hint_05_3),
        Screens.LightTorch.name to listOf(
            R.string.hint_06_1,
            R.string.hint_06_2,
            R.string.hint_06_3),
        Screens.Charging.name to listOf(
            R.string.hint_07_1,
            R.string.hint_07_2),
        Screens.Microphone.name to listOf(
            R.string.hint_08_1,
            R.string.hint_08_2,
            R.string.hint_08_3),
        Screens.DropDownMenu.name to listOf(
            R.string.hint_09_1,
            R.string.hint_09_2,
            R.string.hint_09_3,
            R.string.hint_09_4),
        Screens.LostButton.name to listOf(
            R.string.hint_10_1,
            R.string.hint_10_2),
        Screens.DarkMode.name to listOf(
            R.string.hint_11_1,
            R.string.hint_11_2),
        Screens.AirplaneMode.name to listOf(
            R.string.hint_12_1,
            R.string.hint_12_2),
        Screens.Labyrinth.name to listOf(R.string.hint_13_1),
        Screens.MovingButton.name to listOf(R.string.hint_14_1),
        Screens.ChangeLanguage.name to listOf(
            R.string.hint_15_1,
            R.string.hint_15_2,
            R.string.hint_15_3,
            R.string.hint_15_4,
            R.string.hint_15_5),
        Screens.StepCountingLevel.name to listOf(R.string.hint_16_1),
        Screens.LightSensor.name to listOf(
            R.string.hint_17_1,
            R.string.hint_17_2,
            R.string.hint_17_3),
        Screens.ButtonInHomepage.name to listOf(R.string.hint_18_1),
        Screens.ShutdownDevice.name to listOf(R.string.hint_19_1),
        Screens.Place10Finger.name to listOf(
            R.string.hint_20_1,
            R.string.hint_20_2),
        Screens.Wait20s.name to listOf(R.string.hint_21_1),
        // TODO : add Scroll to find the burron
    )
}