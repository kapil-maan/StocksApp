package com.kapil.stocks.data.model

import com.google.gson.annotations.SerializedName

data class StockDetails(
    val Symbol: String,
    val AssetType: String,
    val Name: String,
    val Description: String,
    val CIK: String,
    val Exchange: String,
    val Currency: String,
    val Country: String,
    val Sector: String,
    val Industry: String,
    val Address: String,
    val OfficialSite: String,
    val FiscalYearEnd: String,
    val LatestQuarter: String,
    val MarketCapitalization: String,
    val EBITDA: String,
    val PERatio: String,
    val PEGRatio: String,
    val BookValue: String,
    val DividendPerShare: String,
    val DividendYield: String,
    val EPS: String,
    val RevenuePerShareTTM: String,
    val ProfitMargin: String,
    val OperatingMarginTTM: String,
    val ReturnOnAssetsTTM: String,
    val ReturnOnEquityTTM: String,
    val RevenueTTM: String,
    val GrossProfitTTM: String,
    val DilutedEPSTTM: String,
    val QuarterlyEarningsGrowthYOY: String,
    val QuarterlyRevenueGrowthYOY: String,
    val AnalystTargetPrice: String,
    val AnalystRatingStrongBuy: String,
    val AnalystRatingBuy: String,
    val AnalystRatingHold: String,
    val AnalystRatingSell: String,
    val AnalystRatingStrongSell: String,
    val TrailingPE: String,
    val ForwardPE: String,
    val PriceToSalesRatioTTM: String,
    val PriceToBookRatio: String,
    val EVToRevenue: String,
    val EVToEBITDA: String,
    val Beta: String,

    // ⭐ ANNOTATIONS ADDED HERE TO FIX GSON SERIALIZATION ⭐
    @SerializedName("52WeekHigh")
    val `52WeekHigh`: String,

    @SerializedName("52WeekLow")
    val `52WeekLow`: String,

    @SerializedName("50DayMovingAverage")
    val `50DayMovingAverage`: String,

    @SerializedName("200DayMovingAverage")
    val `200DayMovingAverage`: String,

    val SharesOutstanding: String,
    val SharesFloat: String,
    val PercentInsiders: String,
    val PercentInstitutions: String,
    val DividendDate: String,
    val ExDividendDate: String
)