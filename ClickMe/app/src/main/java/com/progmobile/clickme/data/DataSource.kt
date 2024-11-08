package com.progmobile.clickme.data

import com.progmobile.clickme.R
import com.progmobile.clickme.Screens

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
    )

    val levelHints = mapOf(
        // map three hints to each level
        Screens.Level_01.name to listOf("Seriously ?"),
        Screens.Level_02.name to listOf(""),
        Screens.Level_03.name to listOf(""),
        Screens.Level_04.name to listOf(""),
        Screens.Level_05.name to listOf(""),
        Screens.Level_06.name to listOf(""),
        Screens.Level_07.name to listOf("I'm kind of tired", "I really lack energy"),
        Screens.Level_08.name to listOf(""),
        Screens.Level_09.name to listOf(""),
        Screens.Level_10.name to listOf("Where can it be ?"),
        Screens.Level_11.name to listOf(""),
    )
    var currentLevel = 10;
}