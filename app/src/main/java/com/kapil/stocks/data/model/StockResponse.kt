package com.kapil.stocks.models

import com.google.gson.annotations.SerializedName

data class StockResponse(
    @SerializedName("Global Quote")
    val globalQuote: GlobalQuote?
)

data class GlobalQuote(
    @SerializedName("01. symbol")
    val symbol: String?,

    @SerializedName("05. price")
    val price: String?,

    @SerializedName("10. change percent")
    val changePercent: String?
)
