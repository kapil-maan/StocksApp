package com.kapil.stocks.data.dummy

import com.kapil.stocks.data.model.Stock

object StockData {
    val stocks = listOf(
        Stock("Apple", "$177.15"),
        Stock("Microsoft", "$322.40"),
        Stock("Amazon", "$131.50", isGainer = false),
        Stock("Tesla", "$199.30"),
        Stock("Google", "$140.00", isGainer = false),
        Stock("Meta", "$299.70"),
        Stock("Netflix", "$440.40", isGainer = false),
        Stock("NVIDIA", "$490.80")
    )

    fun getTopGainers() = stocks.filter { it.isGainer }
    fun getTopLosers() = stocks.filter { !it.isGainer }
}