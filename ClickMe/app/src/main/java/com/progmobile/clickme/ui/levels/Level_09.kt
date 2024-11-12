package com.progmobile.clickme.ui.levels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.progmobile.clickme.ui.LevelButton
import com.progmobile.clickme.ui.UnlockLevel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import com.progmobile.clickme.data.DataSource.levels


/**
 * Composable that allows the user to select the desired action to do and triggers
 * the navigation to next screen
 */
@Composable
fun Level_09(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var sentence by remember { mutableStateOf("") }
    var currentMenu by remember { mutableStateOf(1) }

    LazyColumn(
        modifier = modifier,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
        //verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            Text(
                text = stringResource(id = R.string.level_09),
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
        }

        item {
            Text(
                text = "$sentence",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(100.dp),
                textAlign = TextAlign.Center
            )
        }

        item {
            Box {
                LevelButton(
                    labelResourceId = R.string.button,
                    onClick = { expanded = true },
                    modifier = modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
                if (expanded) {
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        //modifier = modifier
                        //offset = DpOffset(150.dp, 690.dp)
                    ) {
                        when (currentMenu) {
                            1 -> {
                                DropdownMenuItem(
                                    text = { Text("She") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("He") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("This") },
                                    onClick = {
                                        sentence += "This "
                                        currentMenu = 2
                                    },
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("It") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("Button") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                            }

                            2 -> {
                                DropdownMenuItem(
                                    text = { Text("will") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("can") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("button") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("is") },
                                    onClick = {
                                        sentence += "is "
                                        currentMenu = 3
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("can't") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                            }

                            3 -> {
                                DropdownMenuItem(
                                    text = { Text("not") },
                                    onClick = {
                                        sentence += "not "
                                        currentMenu = 4
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("really") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("a") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("very") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("button") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                            }

                            4 -> {
                                DropdownMenuItem(
                                    text = { Text("big") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("it") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("easy") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("button") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("a") },
                                    onClick = {
                                        sentence += "a "
                                        currentMenu = 5
                                    }
                                )
                            }

                            5 -> {
                                DropdownMenuItem(
                                    text = { Text("button") },
                                    onClick = {
                                        sentence += "button."
                                        expanded = false
                                        currentLevel++
                                        navController.navigate(Screens.Level_10.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("joke") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("choice") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("pipe") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                                HorizontalDivider()
                                DropdownMenuItem(
                                    text = { Text("answer") },
                                    onClick = {
                                        navController.navigate(Screens.HomePage.name)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Le menu ne se ferme pas après chaque choix
//        item {
//
//        }
    }
}
@Preview
@Composable
fun StartLevel09Preview() {
    MaterialTheme {
        Level_09(
            navController = rememberNavController(),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}