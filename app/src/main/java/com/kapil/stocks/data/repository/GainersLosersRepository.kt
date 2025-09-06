package com.kapil.stocks.data.repository

import com.kapil.stocks.data.remote.ApiService
import com.kapil.stocks.models.StockResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class GainersLosersRepository(private val apiService: ApiService) {

    private val gainers = listOf("RELIANCE.BSE", "INFY.BSE", "TCS.BSE", "HDFCBANK.BSE")
    private val losers = listOf("ONGC.BSE", "COALINDIA.BSE", "BPCL.BSE", "TATASTEEL.BSE")

    suspend fun fetchGainers(): List<StockResponse?> = fetchStocks(gainers)

    suspend fun fetchLosers(): List<StockResponse?> = fetchStocks(losers)

    private suspend fun fetchStocks(symbols: List<String>): List<StockResponse?> = coroutineScope {
        withContext(Dispatchers.IO) {
            symbols.map { symbol ->
                async {
                    try {
                        apiService.getGlobalQuote(symbol)
                    } catch (e: Exception) {
                        null
                    }
                }
            }.map { it.await() }
        }
    }
}
