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
        Screens.Level_06.name to { navController, modifier -> { Level_06(navController, modifier) } },
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
        Screens.Level_01.name to listOf("Seriously ?"),
        Screens.Level_02.name to listOf(""),
        Screens.Level_03.name to listOf(""),
        Screens.Level_04.name to listOf("Something doesn't seem right.",
            "Can you read the title ?",
            "Maybe if you turn your head it'll be easier."),
        Screens.Level_05.name to listOf(""),
        Screens.Level_06.name to listOf("It's a little dark, isn't it ?","A simple touch can light your way."),
        Screens.Level_07.name to listOf("I'm kind of tired", "I really lack energy"),
        Screens.Level_08.name to listOf(
          "I whisper in the fields and howl in the storms. They follow me, but I have no path. Who am I?",
          "Listen to the colors of the wind",
          "Just breathing can move mountains"),
        Screens.Level_09.name to listOf("Maybe trying all options is not the greatest idea...",
            "Try to expand your artistic knowledge.",
            "Never heard of surrealism ? What a shame.",
            "Really, no idea ? You need to step up your game. Fine, here's the last clue : René Magritte."),
        Screens.Level_10.name to listOf("Where can it be ?"),
        Screens.Level_11.name to listOf(
          "Seek the shadow to find the light, seek the light to find the shadow",
          "Sometimes you have to dive into the depths to see the light"),
        Screens.Level_12.name to listOf( "Head in the clouds"),
        Screens.Level_13.name to listOf(""),
        Screens.Level_14.name to listOf(""),
        Screens.Level_15.name to listOf(
            "Klikit warnon (ma c'hellit)",
            "Klike sou mwen (si ou kapab)",
            "Egin klik nigan (ahal baduzu)",
            "Klicken Sie auf mich (wenn Sie können)",
            "Cliciwch fi (os gallwch chi)")

    )
}