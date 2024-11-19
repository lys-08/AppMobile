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
import com.progmobile.clickme.ui.theme.CustomTheme

object DataSource {
    // Max number of levels to display
    const val LEVEL_NUMBERS = 14
    const val STARTING_LEVEL = 14
    const val MUSIC_DEFAULT = true
    const val SOUND_DEFAULT = true

    // How long to stay pressed on the button of level 2
    const val LEVEL_TWO_LONG_PRESS_DURATION = 3000L

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
        Screens.Level_10.name to { navController, modifier -> { Level_10(navController, modifier) } },
        Screens.Level_11.name to { navController, modifier -> { Level_11(navController, modifier) } },
        Screens.Level_12.name to { navController, modifier -> { Level_12(navController, modifier) } },
        Screens.Level_13.name to { navController, modifier -> { Level_13(navController, modifier) } },
        Screens.Level_14.name to { navController, modifier -> { Level_14(navController, modifier) } },
        Screens.Level_15.name to { navController, modifier -> { Level_15(navController, modifier) } }
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
            R.string.hint_15_5)
    )
}