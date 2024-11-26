package com.progmobile.clickme.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
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
import com.progmobile.clickme.ui.levels.Level_12
import com.progmobile.clickme.ui.levels.Level_13
import com.progmobile.clickme.ui.levels.Level_14
import com.progmobile.clickme.ui.levels.Level_15
import com.progmobile.clickme.ui.levels.Level_16
import com.progmobile.clickme.ui.levels.Level_17
import com.progmobile.clickme.ui.levels.Level_18
import com.progmobile.clickme.ui.levels.Level_19
import com.progmobile.clickme.ui.levels.Level_20
import com.progmobile.clickme.ui.theme.CustomTheme

object DataSource {
    // Max number of levels to display
    const val LEVEL_NUMBERS = 16
    const val STARTING_LEVEL = 16
    const val MUSIC_DEFAULT = true
    const val SOUND_DEFAULT = true
    const val HINT_TEXT_SIZE = 20f

    // Level-specific constants
    const val LEVEL_PATIENCE_LONG_PRESS_DURATION = 3000L
    const val LEVEL_STEP_COUNT_STEP_THRESHOLD = 40

    val levels = listOf(
        Pair(R.string.level_01, Screens.Level_01.name),
        Pair(R.string.level_02, Screens.Level_02.name),
        Pair(R.string.level_03, Screens.Level_03.name),
        Pair(R.string.level_04, Screens.Level_04.name),
        Pair(R.string.level_05, Screens.Level_05.name),
        Pair(R.string.level_06, Screens.Level_06.name),
        Pair(R.string.level_07, Screens.Level_07.name),
        Pair(R.string.level_08, Screens.Level_08.name),
        Pair(R.string.level_09, Screens.Level_09.name),
        Pair(R.string.level_10, Screens.Level_10.name),
        Pair(R.string.level_11, Screens.Level_11.name),
        Pair(R.string.level_12, Screens.Level_12.name),
        Pair(R.string.level_13, Screens.Level_13.name),
        Pair(R.string.level_14, Screens.Level_14.name),
        Pair(R.string.level_15, Screens.Level_15.name),
        Pair(R.string.level_16, Screens.Level_16.name),
        Pair(R.string.level_17, Screens.Level_17.name),
        Pair(R.string.level_18, Screens.Level_18.name),
        Pair(R.string.level_19, Screens.Level_19.name),
        Pair(R.string.level_20, Screens.Level_20.name)
    )

    val levelsMap: Map<String, (NavHostController, Modifier) -> @Composable () -> Unit> = mapOf(
        Screens.Level_01.name to { navController, modifier -> { Level_01(navController, modifier) } },
        Screens.Level_02.name to { navController, modifier -> { Level_02(navController, modifier) } },
        Screens.Level_03.name to { navController, modifier -> { Level_03(navController, modifier) } },
        Screens.Level_04.name to { navController, modifier -> { Level_04(navController, modifier) } },
        Screens.Level_05.name to { navController, modifier -> { Level_05(navController, modifier) } },
        Screens.Level_06.name to { navController, modifier -> { CustomTheme{Level_06(navController, modifier)} } },
        Screens.Level_07.name to { navController, modifier -> { Level_07(navController, modifier) } },
        Screens.Level_08.name to { navController, modifier -> { Level_08(navController, modifier) } },
        Screens.Level_09.name to { navController, modifier -> { Level_09(navController, modifier) } },
        Screens.Level_10.name to { _, modifier -> { Level_10(modifier) } },
        Screens.Level_11.name to { navController, modifier -> { Level_11(navController, modifier) } },
        Screens.Level_12.name to { navController, modifier -> { Level_12(navController, modifier) } },
        Screens.Level_13.name to { navController, modifier -> { Level_13(navController, modifier) } },
        Screens.Level_14.name to { navController, modifier -> { Level_14(navController, modifier) } },
        Screens.Level_15.name to { navController, modifier -> { Level_15(navController, modifier) } },
        Screens.Level_16.name to { navController, modifier -> { Level_16(navController, modifier) } },
        Screens.Level_17.name to { navController, modifier -> { Level_17(navController, modifier) } },
        Screens.Level_18.name to { navController, modifier -> { Level_18(navController, modifier) } },
        Screens.Level_19.name to { navController, modifier -> { Level_19(navController, modifier) } },
        Screens.Level_20.name to { navController, modifier -> { Level_20(navController, modifier) } }
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
        Screens.Level_04.name to listOf(
            R.string.hint_04_1,
            R.string.hint_04_2,
            R.string.hint_04_3),
        Screens.Level_05.name to listOf(
            R.string.hint_05_1,
            R.string.hint_05_2,
            R.string.hint_05_3),
        Screens.Level_06.name to listOf(
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
        Screens.Level_09.name to listOf(
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
        Screens.Level_13.name to listOf(R.string.hint_13_1),
        Screens.Level_14.name to listOf(R.string.hint_14_1),
        Screens.Level_15.name to listOf(
            R.string.hint_15_1,
            R.string.hint_15_2,
            R.string.hint_15_3,
            R.string.hint_15_4,
            R.string.hint_15_5),
        Screens.Level_16.name to listOf(R.string.hint_16_1),
        Screens.Level_17.name to listOf(R.string.hint_17_1),
        Screens.Level_18.name to listOf(R.string.hint_18_1),
        Screens.Level_19.name to listOf(R.string.hint_19_1),
        Screens.Level_20.name to listOf(R.string.hint_20_1),
    )
}