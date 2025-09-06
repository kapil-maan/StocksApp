package com.kapil.stocks.viewmodel

import androidx.lifecycle.ViewModel
import com.kapil.stocks.data.api.ApiClient
import com.kapil.stocks.data.model.Stock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ViewAllViewModel : ViewModel() {

    suspend fun getStockQuotes(symbols: List<String>): List<Stock> {
        val result = mutableListOf<Stock>()
        for (symbol in symbols) {
            val response = withContext(Dispatchers.IO) {
                ApiClient.apiService.getGlobalQuote(symbol = symbol)
            }

            val quote = response["Global Quote"]
            if (quote != null) {
                val price = quote["05. price"] ?: "N/A"
                result.add(Stock(name = symbol, price = "$$price"))
            }
        }
        return result
    }
}
