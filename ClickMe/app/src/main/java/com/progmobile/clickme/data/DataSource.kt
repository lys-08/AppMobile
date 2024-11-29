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
import com.progmobile.clickme.ui.levels.StepCountingLevel
import com.progmobile.clickme.ui.levels.ButtonInHomepage
import com.progmobile.clickme.ui.theme.CustomTheme

object DataSource {
    // State of the application
    var isAppInForeground = mutableStateOf(false)

    // Max number of levels to display
    const val LEVEL_NUMBERS = 20
    const val STARTING_LEVEL = 20
    const val MUSIC_DEFAULT = true
    const val SOUND_DEFAULT = true
    const val HINT_TEXT_SIZE = 20f
    const val IN_BOTTOM_BAR_BUTTONS_SIZE_RELATIVE_TO_SCREEN_WIDTH = 0.2f
    const val IN_PARAMETER_BUTTONS_SPACE_RELATIVE_TO_SCREEN_WIDTH = 0.1f

    // Level-specific constants
    const val LEVEL_PATIENCE_LONG_PRESS_DURATION = 3000L
    const val LEVEL_STEP_COUNT_STEP_THRESHOLD = 40

    val levels = listOf(
        Pair(R.string.level_01, Screens.SimpleButton.name),
        Pair(R.string.level_02, Screens.LongPressButton.name),
        Pair(R.string.level_03, Screens.DoubleButtons.name),
        Pair(R.string.level_04, Screens.Orientation.name),
        Pair(R.string.level_05, Screens.Screenshot.name),
        Pair(R.string.level_06, Screens.LightTorch.name),
        Pair(R.string.level_07, Screens.Charging.name),
        Pair(R.string.level_08, Screens.Microphone.name),
        Pair(R.string.level_09, Screens.DropDownMenu.name),
        Pair(R.string.level_10, Screens.LostButton.name),
        Pair(R.string.level_11, Screens.DarkMode.name),
        Pair(R.string.level_12, Screens.AirplaneMode.name),
        Pair(R.string.level_13, Screens.Labyrinth.name),
        Pair(R.string.level_14, Screens.MovingButton.name),
        Pair(R.string.level_15, Screens.ChangeLanguage.name),
        Pair(R.string.level_16, Screens.StepCountingLevel.name),
        Pair(R.string.level_17, Screens.LightSensor.name),
        Pair(R.string.level_18, Screens.ButtonInHomepage.name),
        Pair(R.string.level_19, Screens.ShutdownDevice.name),
        Pair(R.string.level_20, Screens.Place10Finger.name),
        Pair(R.string.level_21, Screens.Wait20s.name)
    )

    val levelsMap: Map<String, (NavHostController, Modifier) -> @Composable () -> Unit> = mapOf(
       Screens.SimpleButton.name to { navController, modifier -> { SimpleButton(navController, modifier) } },
        Screens.LongPressButton.name to { navController, modifier -> { LongPressButton(navController, modifier) } },
        Screens.DoubleButtons.name to { navController, modifier -> { DoubleButtons(navController, modifier) } },
        Screens.Orientation.name to { navController, modifier -> { Orientation(navController, modifier) } },
        Screens.Screenshot.name to { navController, modifier -> { Screenshot(navController, modifier) } },
        Screens.LightTorch.name to { navController, modifier -> { CustomTheme{LightTorch(navController, modifier)} } },
        Screens.Charging.name to { navController, modifier -> { Charging(navController, modifier) } },
        Screens.Microphone.name to { navController, modifier -> { Microphone(navController, modifier) } },
        Screens.DropDownMenu.name to { navController, modifier -> { DropDownMenu(navController, modifier) } },
        Screens.LostButton.name to { _, modifier -> { LostButton(modifier) } },
        Screens.DarkMode.name to { navController, modifier -> { DarkMode(navController, modifier) } },
        Screens.AirplaneMode.name to { navController, modifier -> { AirplaneMode(navController, modifier) } },
        Screens.Labyrinth.name to { navController, modifier -> { Labyrinth(navController, modifier) } },
        Screens.MovingButton.name to { navController, modifier -> { MovingButton(navController, modifier) } },
        Screens.ChangeLanguage.name to { navController, modifier -> { ChangeLanguage(navController, modifier) } },
        Screens.ChangeLanguage.name to { navController, modifier -> { ChangeLanguage(navController, modifier) } },
        Screens.LightSensor.name to { navController, modifier -> { LightSensor(navController, modifier) } },
        Screens.ButtonInHomepage.name to { navController, modifier -> { ButtonInHomepage(navController, modifier) } },
        Screens.ShutdownDevice.name to { navController, modifier -> { ShutdownDevice(navController, modifier) } },
        Screens.Place10Finger.name to { navController, modifier -> { Place10Finger(navController, modifier) } },
        Screens.Wait20s.name to { navController, modifier -> { Wait20s(navController, modifier) } }
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
    )
}