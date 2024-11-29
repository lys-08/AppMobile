package com.progmobile.clickme.ui.levels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.progmobile.clickme.R
import com.progmobile.clickme.Screens
import com.progmobile.clickme.ui.UnlockLevel
import com.progmobile.clickme.ui.theme.ClickMeTheme

/**
 * Composable that displays the first level of the game.
 * Only click on the button to go to the next level.
 */
@Composable
fun ScrollToFindTheButton(
    idLevel: Int,
    nextLevel: String,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10000.dp)
    ) {
        // Title
         item {
             Text(
                 text = stringResource(id = R.string.level_22),
                 style = MaterialTheme.typography.displayLarge,
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(vertical = 16.dp),
                 textAlign = TextAlign.Center
             )
         }

        // Level button
        item {
            UnlockLevel(
            labelResourceId = R.string.button,
            level = idLevel,
            modifier = Modifier,
            levelName = nextLevel,
            navController = navController
        ) }
    }
}

@Preview
@Composable
fun StartScrollToFindTheButtonPreview() {
    ClickMeTheme {
        ScrollToFindTheButton(
            idLevel = -1,
            nextLevel = Screens.HomePage.name,
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}