package com.kapil.stocks.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kapil.stocks.R
import com.kapil.stocks.adapters.StockAdapter
import com.kapil.stocks.constants.Constants
import com.kapil.stocks.data.fallback.AllStockInfoFallback
import com.kapil.stocks.data.model.Stock
import com.kapil.stocks.databinding.ActivityMainBinding
import com.kapil.stocks.viewmodel.STOCKS_LIST_TYPE
import com.kapil.stocks.viewmodel.StockListViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: StockListViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchAdapter: StockAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[StockListViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.parent) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(0, statusBarInsets.top, 0, 0)
            insets
        }

        setupRecyclerViews()
        initData()
        setupBottomNav()
        setupViewAllClickListeners()
        setupSearch()
    }

    private fun setupSearch() {
        searchAdapter = StockAdapter { stock ->
            navigateToDetailActivity(stock.name)
        }
        binding.recyclerSearchResults.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerSearchResults.adapter = searchAdapter

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                if (query.isNotEmpty()) {
                    binding.recyclerSearchResults.visibility = View.VISIBLE
                    binding.scrollView.visibility = View.GONE
                    filterAllStocks(query)
                } else {
                    binding.recyclerSearchResults.visibility = View.GONE
                    binding.scrollView.visibility = View.VISIBLE
                    searchAdapter.updateData(emptyList())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun filterAllStocks(query: String) {
        val allStocks = AllStockInfoFallback.allStocks.values

        val filteredList = allStocks.filter { stockDetails ->

            stockDetails.Symbol.lowercase().contains(query.lowercase()) ||
                    stockDetails.Name.lowercase().contains(query.lowercase())
        }.map { stockDetails ->
            Stock(name = stockDetails.Symbol, price = "N/A")
        }

        searchAdapter.updateData(filteredList)
    }



    private fun initData() {
        lifecycleScope.launch {
            viewModel.fetchTopGainersLosers(STOCKS_LIST_TYPE.GAINERS).collect { data ->
                (binding.recyclerGainers.adapter as StockAdapter).updateData(data.take(4))
            }
        }
        lifecycleScope.launch {
            viewModel.fetchTopGainersLosers(STOCKS_LIST_TYPE.LOSERS).collect { data ->
                (binding.recyclerLosers.adapter as StockAdapter).updateData(data.take(4))
            }
        }
    }

    private fun setupRecyclerViews() {
        binding.recyclerGainers.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerGainers.adapter = StockAdapter { stock ->
            navigateToDetailActivity(stock.name)
        }

        binding.recyclerLosers.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerLosers.adapter = StockAdapter { stock ->
            navigateToDetailActivity(stock.name)
        }
    }

    private fun navigateToDetailActivity(stockName: String) {
        val intent = Intent(this, StockDetailsActivity::class.java)
        intent.putExtra(Constants.COMPANY_NAME, stockName)
        startActivity(intent)
    }

    private fun setupBottomNav() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> true
                R.id.nav_watchlist -> {
                    startActivity(Intent(this, WatchlistActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun setupViewAllClickListeners() {
        binding.viewAllGainers.setOnClickListener {
            val intent = Intent(this, StockListActivity::class.java)
            intent.putExtra(Constants.LIST_TYPE, STOCKS_LIST_TYPE.GAINERS.toString())
            startActivity(intent)
        }

        binding.viewAllLosers.setOnClickListener {
            val intent = Intent(this, StockListActivity::class.java)
            intent.putExtra(Constants.LIST_TYPE, STOCKS_LIST_TYPE.LOSERS.toString())
            startActivity(intent)
        }
    }
}