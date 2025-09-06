package com.kapil.stocks.data.model

import java.io.Serializable

data class Stock(
    val name: String,  // This will act as the stock symbol (e.g., "AAPL")
    val price: String,    // Current stock price
    val isGainer: Boolean = true,   // Optional: used to indicate if it's a gainer or loser
    val changePercent: Double = 0.0
) : Serializable
