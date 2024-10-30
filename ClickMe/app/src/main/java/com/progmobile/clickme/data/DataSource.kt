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

object DataSource {
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
    )

    val levelsMap: Map<String, (NavHostController, Modifier) -> @Composable () -> Unit> = mapOf(
        Screens.Level_01.name to { navController, modifier -> { Level_01(navController, modifier) } },
        Screens.Level_02.name to { navController, modifier -> { Level_02(navController, modifier) } },
        Screens.Level_03.name to { navController, modifier -> { Level_03(navController, modifier) } },
        Screens.Level_04.name to { navController, modifier -> { Level_04(navController, modifier) } },
        Screens.Level_05.name to { navController, modifier -> { Level_05(navController, modifier) } },
        Screens.Level_06.name to { navController, modifier -> { Level_06(navController, modifier) } },
        Screens.Level_07.name to { navController, modifier -> { Level_07(navController, modifier) } },
        Screens.Level_08.name to { navController, modifier -> { Level_08(navController, modifier) } },
        Screens.Level_09.name to { navController, modifier -> { Level_09(navController, modifier) } },
        Screens.Level_10.name to { navController, modifier -> { Level_10(navController, modifier) } },
        Screens.Level_11.name to { navController, modifier -> { Level_11(navController, modifier) } },
        Screens.Level_12.name to { navController, modifier -> { Level_12(navController, modifier) } }
    )

    var currentLevel = 11;
}