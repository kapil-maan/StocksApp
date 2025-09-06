package com.kapil.stocks.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("query")
    suspend fun getGlobalQuote(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = "FB3R35XFKQUBW7T0"
    ): Map<String, Map<String, String>>
}
