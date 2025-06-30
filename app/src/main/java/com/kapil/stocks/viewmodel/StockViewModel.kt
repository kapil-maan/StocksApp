package com.kapil.stocks.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapil.stocks.constants.Constants
import com.kapil.stocks.data.model.StockDetails
import com.kapil.stocks.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StockViewModel : ViewModel() {
    // private stuff
    private val _stockdetails: MutableStateFlow<StockDetails?> = MutableStateFlow(null)
    private val _authStatus: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        fetchStockDetails(stockName = "META")
    }

    // Public
    val stockDetails: StateFlow<StockDetails?> = _stockdetails.asStateFlow();

    fun fetchStockDetails(stockName: String){
        Log.d(Constants.TAG, "fetch stock details called")
        viewModelScope.launch {
            val stockApiInstance = ApiService.getStocksApi()
            val response = stockApiInstance.getStockDetails("META", "AII507GDUFZTDWJ3");
            _stockdetails.emit(response.body())
            Log.d(Constants.TAG, "API response ${response} ${response.body()}")

        }
    }
}