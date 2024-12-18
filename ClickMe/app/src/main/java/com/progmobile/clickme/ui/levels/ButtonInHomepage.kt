package com.progmobile.clickme.ui.levels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.progmobile.clickme.R
import com.progmobile.clickme.ui.IsLastPage
import com.progmobile.clickme.ui.theme.ClickMeTheme

/**
 * Composable that displays the level where the button is hidden in the Homepage.
 * Go to home page to see the button (available one time).
 */
@Composable
fun ButtonInHomepage(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.level_button_in_home_page),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )
        
        IsLastPage(isLastPage = true)

        //See HomePage.kt for more information
    }
}

@Preview
@Composable
fun StartButtonInHomepagePreview() {
    ClickMeTheme {
        ButtonInHomepage(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}