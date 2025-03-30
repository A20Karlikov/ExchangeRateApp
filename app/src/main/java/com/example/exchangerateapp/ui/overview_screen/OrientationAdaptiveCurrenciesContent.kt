package com.example.exchangerateapp.ui.overview_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.exchangerateapp.R

@Composable
fun OrientationAdaptiveCurrenciesContent(
    contentFrom: @Composable () -> Unit,
    contentTo: @Composable () -> Unit,
) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.screenWidthDp < configuration.screenHeightDp

    if (isPortrait) {
        Column {
            Text(text = stringResource(R.string.overview_screen_currency_section_convert_from_title))
            Spacer(modifier = Modifier.height(4.dp))
            contentFrom()

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = stringResource(R.string.overview_screen_currency_section_convert_to_title))
            Spacer(modifier = Modifier.height(4.dp))
            contentTo()
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1F),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = stringResource(R.string.overview_screen_currency_section_convert_from_title))
                contentFrom()
            }

            Column(
                modifier = Modifier.weight(1F),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = stringResource(R.string.overview_screen_currency_section_convert_from_title))
                contentTo()
            }
        }
    }
}