package com.example.exchangerateapp.ui.overview_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerateapp.domain.DataResult
import com.example.exchangerateapp.domain.GetExchangeRatesUseCase
import com.example.exchangerateapp.domain.model.ExchangeRates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    internal val viewState = _viewState.asStateFlow()

    init {
        getExchangeRates()
    }

    fun getExchangeRates(baseCode: String = INITIAL_BASE_CODE) {
        viewModelScope.launch {
            _viewState.value = when (val result = getExchangeRatesUseCase.execute(baseCode)) {
                is DataResult.Failure -> ViewState.Error
                is DataResult.Success -> ViewState.Content(result.data)
            }
        }
    }

    sealed class ViewState {
        data object Loading : ViewState()
        data class Content(val content: ExchangeRates) : ViewState()
        data object Error : ViewState()
    }
}

private const val INITIAL_BASE_CODE = "USD"