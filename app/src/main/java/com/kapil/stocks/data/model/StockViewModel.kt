package com.kapil.stocks.data.model

import java.io.Serializable

data class Stock(
    val name: String,
    val price: String,
    val isGainer: Boolean = true
) : Serializable
