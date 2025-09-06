package com.kapil.stocks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kapil.stocks.data.dummy.StockData
import com.kapil.stocks.data.model.Stock

class StockViewModel : ViewModel() {
    private val _gainers = MutableLiveData<List<Stock>>()
    val gainers: LiveData<List<Stock>> get() = _gainers

    private val _losers = MutableLiveData<List<Stock>>()
    val losers: LiveData<List<Stock>> get() = _losers

    fun loadData() {
        _gainers.value = StockData.getTopGainers()
        _losers.value = StockData.getTopLosers()
    }
}
