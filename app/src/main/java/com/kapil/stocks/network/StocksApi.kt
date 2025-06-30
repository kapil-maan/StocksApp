package com.kapil.stocks.network

import com.kapil.stocks.data.model.StockDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StocksApi {
    @GET("/query?function=OVERVIEW")
    suspend fun getStockDetails(@Query("symbol") companyName: String, @Query("apikey") apiKey: String): Response<StockDetails>
}