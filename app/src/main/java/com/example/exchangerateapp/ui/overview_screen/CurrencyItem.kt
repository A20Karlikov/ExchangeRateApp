package com.example.exchangerateapp.ui.overview_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.exchangerateapp.R
import com.example.exchangerateapp.ui.theme.White

@Composable
internal fun CurrencyItem(
    currencies: Map<String, Double>,
    selectedCurrency: Pair<String, Double>,
    selectedCounterCurrency: Pair<String, Double>? = null,
    onItemSelected: (index: Int) -> Unit,
) {
    val isDropdownExpanded = remember { mutableStateOf(false) }

    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = White,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 8.dp, horizontal = 24.dp)
                .clickable { isDropdownExpanded.value = true },
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = selectedCurrency.first
            )

            Text(
                text = selectedCounterCurrency?.let {
                    String.format(
                        stringResource(R.string.overview_screen_currency_section_subtitle),
                        it.first,
                        selectedCurrency.second,
                        selectedCurrency.first
                    )
                } ?: stringResource(R.string.overview_screen_currency_section_subtitle_none)
            )
        }

        DropdownMenu(
            expanded = isDropdownExpanded.value,
            onDismissRequest = { isDropdownExpanded.value = false }
        ) {
            currencies.keys.forEachIndexed { index, currency ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = currency,
                            style = MaterialTheme.typography.bodyMedium
                        )
                           },
                    onClick = {
                        isDropdownExpanded.value = false
                        onItemSelected(index)
                    }
                )
            }
        }
    }
}