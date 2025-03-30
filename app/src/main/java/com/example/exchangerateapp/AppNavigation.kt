package com.example.exchangerateapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.exchangerateapp.ui.currency_conversion_screen.CurrencyConversionScreen
import com.example.exchangerateapp.ui.overview_screen.OverviewScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "overview") {
        composable(route = "overview") {
            OverviewScreen { currencyFrom, currencyTo, amount ->
                navController.navigate(
                    "currency_conversion/${currencyFrom.first}/${currencyFrom.second}/${currencyTo.first}/${currencyTo.second}/$amount"
                )
            }
        }

        composable(
            route = "currency_conversion/{currency_a}/{value_currency_a}/{currency_b}/{value_currency_b}/{amount}",
            arguments = listOf(
                navArgument("currency_a") { type = NavType.StringType },
                navArgument("value_currency_a") { type = NavType.FloatType },
                navArgument("currency_b") { type = NavType.StringType },
                navArgument("value_currency_b") { type = NavType.FloatType },
                navArgument("amount") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val currencyA = backStackEntry.arguments?.getString("currency_a") ?: ""
            val valueCurrencyA = backStackEntry.arguments?.getFloat("value_currency_a") ?: 0.0
            val currencyB = backStackEntry.arguments?.getString("currency_b") ?: ""
            val valueCurrencyB = backStackEntry.arguments?.getFloat("value_currency_b") ?: 0.0
            val amount = backStackEntry.arguments?.getString("amount") ?: ""

            CurrencyConversionScreen(
                initialCurrencyFrom = Pair(currencyA, valueCurrencyA.toDouble()),
                initialCurrencyTo = Pair(currencyB, valueCurrencyB.toDouble()),
                amountValue = amount,
                navController = navController
            )
        }
    }
}