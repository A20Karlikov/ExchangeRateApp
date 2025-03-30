package com.example.exchangerateapp.ui.overview_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.exchangerateapp.ui.theme.ExchangeRateAppTheme
import com.example.exchangerateapp.R
import com.example.exchangerateapp.ui.theme.LightGray
import com.example.exchangerateapp.ui.theme.White
import com.example.exchangerateapp.utils.isValid
import com.example.exchangerateapp.utils.setCorrectSeparator

@Composable
fun OverviewScreen(
    onGoToConversionClicked: (
        currencyFrom: Pair<String, Double>,
        currencyTo: Pair<String, Double>,
        amount: String
    ) -> Unit
) {
    val viewModel: OverviewViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsState(initial = OverviewViewModel.ViewState.Loading)

    val focusManager = LocalFocusManager.current

    when (state) {
        is OverviewViewModel.ViewState.Content -> {
            val conversionRates =
                (state as OverviewViewModel.ViewState.Content).content.conversionRates

            val fromCurrencySelected =
                rememberSaveable { mutableStateOf(conversionRates.toList()[0]) }

            val toCurrencySelected =
                rememberSaveable { mutableStateOf(conversionRates.toList()[1]) }

            LaunchedEffect(key1 = conversionRates) {
                val updatedCurrencyTo =
                    conversionRates.entries.find { it.key == toCurrencySelected.value.first }
                updatedCurrencyTo?.let { toCurrencySelected.value = it.toPair() }
            }

            val amount = rememberSaveable { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = LightGray)
                    .padding(bottom = 48.dp, top = 32.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.overview_screen_title),
                    style = MaterialTheme.typography.titleLarge.copy(fontStyle = FontStyle.Italic)
                )


                Column(
                    modifier = Modifier
                        .weight(1F)
                        .padding(horizontal = 32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    OrientationAdaptiveCurrenciesContent(
                        contentFrom = {
                            CurrencyItem(
                                currencies = conversionRates,
                                selectedCurrency = fromCurrencySelected.value,
                                onItemSelected = { index ->
                                    conversionRates.toList()[index].also {
                                        fromCurrencySelected.value = it
                                        viewModel.getExchangeRates(baseCode = it.first)
                                    }
                                },
                            )
                        },
                        contentTo = {
                            CurrencyItem(
                                currencies = conversionRates,
                                selectedCurrency = toCurrencySelected.value,
                                selectedCounterCurrency = fromCurrencySelected.value,
                                onItemSelected = { index ->
                                    toCurrencySelected.value = conversionRates.toList()[index]
                                },
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = White),
                        value = amount.value,
                        onValueChange = { am ->
                            if (am.isValid()) amount.value = am.setCorrectSeparator()
                        },
                        placeholder = {
                            Text(text = stringResource(R.string.overview_screen_currency_section_amount_field_placeholder))
                        },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        )
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = fromCurrencySelected.value.first != toCurrencySelected.value.first && amount.value.isNotEmpty(),
                    onClick = {
                        onGoToConversionClicked(
                            fromCurrencySelected.value,
                            toCurrencySelected.value,
                            amount.value
                        )
                    }
                ) {
                    Text(
                        text = stringResource(R.string.overview_screen_go_to_details_button_title),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }

        OverviewViewModel.ViewState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.overview_screen_error_message),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        OverviewViewModel.ViewState.Loading -> Unit // Handling is not necessary in this specific case
    }
}

@Preview
@Composable
private fun ExchangeRateOverviewScreenPreview() {
    ExchangeRateAppTheme {
        OverviewScreen(
            onGoToConversionClicked = { _, _, _ -> }
        )
    }
}