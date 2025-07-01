package com.kapil.stocks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapil.stocks.data.repository.GainersLosersRepository
import com.kapil.stocks.models.StockResponse
import kotlinx.coroutines.launch

class GainersLosersViewModel(private val repository: GainersLosersRepository) : ViewModel() {

    private val _gainers = MutableLiveData<List<StockResponse?>>()
    val gainers: LiveData<List<StockResponse?>> get() = _gainers

    private val _losers = MutableLiveData<List<StockResponse?>>()
    val losers: LiveData<List<StockResponse?>> get() = _losers

    fun loadTopGainers() {
        viewModelScope.launch {
            _gainers.value = repository.fetchGainers()
        }
    }

    fun loadTopLosers() {
        viewModelScope.launch {
            _losers.value = repository.fetchLosers()
        }
    }
}
