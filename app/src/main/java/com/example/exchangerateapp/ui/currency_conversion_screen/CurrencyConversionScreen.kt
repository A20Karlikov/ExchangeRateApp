package com.example.exchangerateapp.ui.currency_conversion_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.exchangerateapp.R
import com.example.exchangerateapp.ui.theme.LightGray
import com.example.exchangerateapp.ui.theme.White
import com.example.exchangerateapp.utils.isValid
import com.example.exchangerateapp.utils.setCorrectSeparator

@Composable
fun CurrencyConversionScreen(
    initialCurrencyFrom: Pair<String, Double>,
    initialCurrencyTo: Pair<String, Double>,
    amountValue: String,
    navController: NavController
) {

    val currencyFrom = remember { mutableStateOf(initialCurrencyFrom) }
    val currencyTo = remember { mutableStateOf(initialCurrencyTo) }
    val amount = remember { mutableStateOf(amountValue) }
    
    val focusManager = LocalFocusManager.current

    BackHandler { navController.popBackStack() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightGray)
            .padding(bottom = 48.dp, top = 32.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.currency_conversion_screen_title),
            style = MaterialTheme.typography.titleLarge.copy(fontStyle = FontStyle.Italic)
        )


        Column(
            modifier = Modifier
                .weight(1F)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .weight(1F)
                        .background(color = White, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 16.dp),
                    text = currencyFrom.value.first,
                    textAlign = TextAlign.Center
                )

                Icon(
                    painter = painterResource(R.drawable.icons_replace),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            val currA = currencyFrom.value
                            val currB = currencyTo.value

                            currencyFrom.value = currB.copy(second = 1.0)
                            currencyTo.value = currA.copy(second = 1.0 / currB.second)
                        },
                    contentDescription = ""
                )

                Text(
                    modifier = Modifier
                        .weight(1F)
                        .background(color = White, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 16.dp),
                    text = currencyTo.value.first,
                    textAlign = TextAlign.Center
                )
            }

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

            if (amount.value.isNotEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = String.format(
                        stringResource(R.string.currency_conversion_screen_summary_conversion_text),
                        amount.value,
                        currencyFrom.value.first,
                        amount.value.toDouble() * currencyTo.value.second,
                        currencyTo.value.first
                    ),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { navController.popBackStack() }
        ) {
            Text(
                text = stringResource(R.string.currency_conversion_screen_go_back_button),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun CurrencyConversionScreenPreview() {
    CurrencyConversionScreen(
        initialCurrencyFrom = Pair("USD", 1.0),
        initialCurrencyTo = Pair("EUR", 1.1),
        amountValue = "150",
        navController = NavController(LocalContext.current)
    )
}