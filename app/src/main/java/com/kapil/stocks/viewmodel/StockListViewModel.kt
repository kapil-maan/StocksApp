package com.kapil.stocks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapil.stocks.data.model.Stock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StockListViewModel : ViewModel() {

    private val _stocks = MutableStateFlow<List<Stock>>(emptyList())
    val stocks: StateFlow<List<Stock>> = _stocks

    private val dummyGainers = listOf(
        Stock("AAPL", "$198.45", true),
        Stock("MSFT", "$339.12", true),
        Stock("TSLA", "$267.23", true),
        Stock("META", "$345.01", true),
        Stock("NVDA", "$129.50", true)
    )

    private val dummyLosers = listOf(
        Stock("INTC", "$31.67", false),
        Stock("AMD", "$134.22", false),
        Stock("GOOGL", "$172.65", false),
        Stock("BABA", "$82.44", false),
        Stock("NFLX", "$639.90", false)
    )

    fun loadStocks(type: String) {
        viewModelScope.launch {
            val data = if (type == "losers") dummyLosers else dummyGainers
            _stocks.emit(data)
        }
    }
}
