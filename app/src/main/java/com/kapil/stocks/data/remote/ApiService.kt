package com.kapil.stocks.data.remote

import com.kapil.stocks.models.StockResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("query?function=GLOBAL_QUOTE")
    suspend fun getGlobalQuote(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = com.kapil.stocks.BuildConfig.ALPHA_VANTAGE_API_KEY

    ): StockResponse
}
