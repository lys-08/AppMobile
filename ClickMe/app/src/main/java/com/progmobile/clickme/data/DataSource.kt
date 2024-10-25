package com.progmobile.clickme.data

import com.progmobile.clickme.R
import com.progmobile.clickme.Screens

object DataSource {
    val levels = listOf(
        Pair(R.string.level_01, Screens.Level_01.name),
        Pair(R.string.level_02, Screens.Level_02.name),
        Pair(R.string.level_03, Screens.Level_03.name),
        Pair(R.string.level_04, Screens.Level_04.name),
        Pair(R.string.level_05, Screens.Level_05.name)
    )
    var currentLevel = 0;
}