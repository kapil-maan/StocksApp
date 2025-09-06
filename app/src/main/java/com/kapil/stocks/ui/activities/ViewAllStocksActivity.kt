package com.kapil.stocks.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kapil.stocks.adapters.StockAdapter
import com.kapil.stocks.databinding.ActivityViewAllStocksBinding
import com.kapil.stocks.viewmodel.ViewAllViewModel
import kotlinx.coroutines.launch

class ViewAllStocksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewAllStocksBinding
    private lateinit var viewModel: ViewAllViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllStocksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ViewAllViewModel::class.java]

        val isGainers = intent.getBooleanExtra("isGainers", true)
        supportActionBar?.title = if (isGainers) "Top Gainers" else "Top Losers"

        setupRecyclerView()
        fetchStockData(isGainers)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun fetchStockData(isGainers: Boolean) {
        val symbols = if (isGainers) {
            listOf("AAPL", "MSFT", "GOOGL", "NVDA") // Top Gainers (hardcoded)
        } else {
            listOf("META", "TSLA", "AMZN", "NFLX") // Top Losers (hardcoded)
        }

        lifecycleScope.launch {
            try {
                val stocks = viewModel.getStockQuotes(symbols)
                binding.recyclerView.adapter = StockAdapter(stocks) { stock ->
                    // Optional: Handle click
                }
            } catch (e: Exception) {
                Toast.makeText(this@ViewAllStocksActivity, "Failed to load stocks", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
}
