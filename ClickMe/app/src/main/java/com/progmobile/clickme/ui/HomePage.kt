package com.progmobile.clickme.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
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
import com.progmobile.clickme.data.DataSource.currentLevel
import com.progmobile.clickme.data.DataSource.levels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource


/**
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
 */
@Composable
fun HomePage(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        // Levels button
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
          ) {
              //Enable buttons from unlocked levels
              items((0..currentLevel).toList()) { i ->
                  LevelButton(
                      labelResourceId = levels[i].first,
                      onClick = { navController.navigate(levels[i].second) }
                  )
              }
              //Disable buttons from locked levels
              items((currentLevel + 1..levels.size - 1).toList()) { i ->
                  LevelButtonLocked(
                      labelResourceId = levels[i].first,
                      onClick = { navController.navigate(levels[i].second) }
                  )
              }
        }

    }
}

/**
 * Customizable button composable that displays the [labelResourceId]
 * and triggers [onClick] lambda when this composable is clicked
 */
@Composable
fun LevelButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 150.dp)
            .widthIn()
    ) {
        Text(stringResource(labelResourceId))
    }
}

@Composable
fun LevelButtonLocked(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp),
        enabled = false
    ) {
        Image(
            painter = painterResource(id = R.drawable.lock_icon),
            contentDescription = "Locked levels",
            modifier = Modifier
                .background(Color.Transparent)
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun UnlockLevel(
    @StringRes labelResourceId: Int,
    level: Int,
    modifier: Modifier = Modifier,
    levelName: String,
    navController: NavHostController
) {
    LevelButton(
        labelResourceId = labelResourceId,
        onClick = { navController.navigate(levelName)
            if (currentLevel < level) {
                currentLevel++
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}


@Preview
@Composable
fun StartHomePagePreview() {
    MaterialTheme {
        HomePage(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}