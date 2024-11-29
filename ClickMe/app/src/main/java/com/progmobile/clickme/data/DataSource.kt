package com.progmobile.clickme.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
import com.progmobile.clickme.ui.levels.Level_01
import com.progmobile.clickme.ui.levels.Level_02
import com.progmobile.clickme.ui.levels.Level_03
import com.progmobile.clickme.ui.levels.Orientation
import com.progmobile.clickme.ui.levels.Screenshot
import com.progmobile.clickme.ui.levels.LightTorch
import com.progmobile.clickme.ui.levels.Level_07
import com.progmobile.clickme.ui.levels.Level_08
import com.progmobile.clickme.ui.levels.DropDownMenu
import com.progmobile.clickme.ui.levels.Level_10
import com.progmobile.clickme.ui.levels.Level_11
import com.progmobile.clickme.ui.levels.Level_12
import com.progmobile.clickme.ui.levels.Labyrinth
import com.progmobile.clickme.ui.levels.Level_14
import com.progmobile.clickme.ui.levels.ChangeLanguage
import com.progmobile.clickme.ui.levels.Level_16
import com.progmobile.clickme.ui.levels.Level_17
import com.progmobile.clickme.ui.levels.ButtonInHomepage
import com.progmobile.clickme.ui.levels.Level_19
import com.progmobile.clickme.ui.levels.Level_20
import com.progmobile.clickme.ui.levels.Level_21
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
        Pair(R.string.level_01, Screens.Level_01.name),
        Pair(R.string.level_02, Screens.Level_02.name),
        Pair(R.string.level_03, Screens.Level_03.name),
        Pair(R.string.level_04, Screens.Orientation.name),
        Pair(R.string.level_05, Screens.Level_05.name),
        Pair(R.string.level_06, Screens.LightTorch.name),
        Pair(R.string.level_07, Screens.Level_07.name),
        Pair(R.string.level_08, Screens.Level_08.name),
        Pair(R.string.level_09, Screens.DropDownMenu.name),
        Pair(R.string.level_10, Screens.Level_10.name),
        Pair(R.string.level_11, Screens.Level_11.name),
        Pair(R.string.level_12, Screens.Level_12.name),
        Pair(R.string.level_13, Screens.Labyrinth.name),
        Pair(R.string.level_14, Screens.Level_14.name),
        Pair(R.string.level_15, Screens.ChangeLanguage.name),
        Pair(R.string.level_16, Screens.Level_16.name),
        Pair(R.string.level_17, Screens.Level_17.name),
        Pair(R.string.level_18, Screens.ButtonInHomepage.name),
        Pair(R.string.level_19, Screens.Level_19.name),
        Pair(R.string.level_20, Screens.Level_20.name),
        Pair(R.string.level_21, Screens.Level_21.name)
    )

    val levelsMap: Map<String, (NavHostController, Modifier) -> @Composable () -> Unit> = mapOf(
        Screens.Level_01.name to { navController, modifier -> { Level_01(navController, modifier) } },
        Screens.Level_02.name to { navController, modifier -> { Level_02(navController, modifier) } },
        Screens.Level_03.name to { navController, modifier -> { Level_03(navController, modifier) } },
        Screens.Orientation.name to { navController, modifier -> { Orientation(navController, modifier) } },
        Screens.Level_05.name to { navController, modifier -> { Screenshot(navController, modifier) } },
        Screens.LightTorch.name to { navController, modifier -> { CustomTheme{LightTorch(navController, modifier)} } },
        Screens.Level_07.name to { navController, modifier -> { Level_07(navController, modifier) } },
        Screens.Level_08.name to { navController, modifier -> { Level_08(navController, modifier) } },
        Screens.DropDownMenu.name to { navController, modifier -> { DropDownMenu(navController, modifier) } },
        Screens.Level_10.name to { _, modifier -> { Level_10(modifier) } },
        Screens.Level_11.name to { navController, modifier -> { Level_11(navController, modifier) } },
        Screens.Level_12.name to { navController, modifier -> { Level_12(navController, modifier) } },
        Screens.Labyrinth.name to { navController, modifier -> { Labyrinth(navController, modifier) } },
        Screens.Level_14.name to { navController, modifier -> { Level_14(navController, modifier) } },
        Screens.ChangeLanguage.name to { navController, modifier -> { ChangeLanguage(navController, modifier) } },
        Screens.Level_16.name to { navController, modifier -> { Level_16(navController, modifier) } },
        Screens.Level_17.name to { navController, modifier -> { Level_17(navController, modifier) } },
        Screens.ButtonInHomepage.name to { navController, modifier -> { ButtonInHomepage(navController, modifier) } },
        Screens.Level_19.name to { navController, modifier -> { Level_19(navController, modifier) } },
        Screens.Level_20.name to { navController, modifier -> { Level_20(navController, modifier) } },
        Screens.Level_21.name to { navController, modifier -> { Level_21(navController, modifier) } }
    )

    val levelHints = mapOf(
        // map three hints to each level
        Screens.Level_01.name to listOf(
            R.string.hint_01_1,
            R.string.hint_01_2,
            R.string.hint_01_3),
        Screens.Level_02.name to listOf(
            R.string.hint_02_1,
            R.string.hint_02_3,
            R.string.hint_02_3),
        Screens.Level_03.name to listOf(
            R.string.hint_03_1,
            R.string.hint_03_2,
            R.string.hint_03_3),
        Screens.Orientation.name to listOf(
            R.string.hint_04_1,
            R.string.hint_04_2,
            R.string.hint_04_3),
        Screens.Level_05.name to listOf(
            R.string.hint_05_1,
            R.string.hint_05_2,
            R.string.hint_05_3),
        Screens.LightTorch.name to listOf(
            R.string.hint_06_1,
            R.string.hint_06_2,
            R.string.hint_06_3),
        Screens.Level_07.name to listOf(
            R.string.hint_07_1,
            R.string.hint_07_2),
        Screens.Level_08.name to listOf(
            R.string.hint_08_1,
            R.string.hint_08_2,
            R.string.hint_08_3),
        Screens.DropDownMenu.name to listOf(
            R.string.hint_09_1,
            R.string.hint_09_2,
            R.string.hint_09_3,
            R.string.hint_09_4),
        Screens.Level_10.name to listOf(
            R.string.hint_10_1,
            R.string.hint_10_2),
        Screens.Level_11.name to listOf(
            R.string.hint_11_1,
            R.string.hint_11_2),
        Screens.Level_12.name to listOf(
            R.string.hint_12_1,
            R.string.hint_12_2),
        Screens.Labyrinth.name to listOf(R.string.hint_13_1),
        Screens.Level_14.name to listOf(R.string.hint_14_1),
        Screens.ChangeLanguage.name to listOf(
            R.string.hint_15_1,
            R.string.hint_15_2,
            R.string.hint_15_3,
            R.string.hint_15_4,
            R.string.hint_15_5),
        Screens.Level_16.name to listOf(R.string.hint_16_1),
        Screens.Level_17.name to listOf(
            R.string.hint_17_1,
            R.string.hint_17_2,
            R.string.hint_17_3),
        Screens.ButtonInHomepage.name to listOf(R.string.hint_18_1),
        Screens.Level_19.name to listOf(R.string.hint_19_1),
        Screens.Level_20.name to listOf(
            R.string.hint_20_1,
            R.string.hint_20_2),
        Screens.Level_21.name to listOf(R.string.hint_21_1),
    )
}