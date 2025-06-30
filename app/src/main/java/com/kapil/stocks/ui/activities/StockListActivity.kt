package com.kapil.stocks.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kapil.stocks.adapters.StockAdapter
import com.kapil.stocks.databinding.ActivityStockListBinding
import com.kapil.stocks.viewmodel.StockListViewModel
import kotlinx.coroutines.launch

class StockListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockListBinding
    private lateinit var viewModel: StockListViewModel
    private lateinit var stockType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        stockType = intent.getStringExtra("type") ?: "gainers"

        viewModel = ViewModelProvider(this)[StockListViewModel::class.java]

        setupRecycler()
        observeStocks()

        viewModel.loadStocks(stockType)
    }

    private fun setupRecycler() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun observeStocks() {
        lifecycleScope.launch {
            viewModel.stocks.collect {
                binding.recyclerView.adapter = StockAdapter(it) { stock ->
                    // handle stock click
                }
            }
        }
    }
}
