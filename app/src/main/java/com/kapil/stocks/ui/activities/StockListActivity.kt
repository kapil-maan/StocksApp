package com.kapil.stocks.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kapil.stocks.adapters.StockAdapter
import com.kapil.stocks.constants.Constants
import com.kapil.stocks.data.model.Stock
import com.kapil.stocks.databinding.ActivityStockListBinding
import com.kapil.stocks.services.SharedPreferences
import com.kapil.stocks.viewmodel.STOCKS_LIST_TYPE
import com.kapil.stocks.viewmodel.StockListViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

class StockListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockListBinding
    private lateinit var viewModel: StockListViewModel
    private lateinit var listType: STOCKS_LIST_TYPE
    private var watchListName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.parent) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(0, statusBarInsets.top, 0, 0)
            insets
        }

        listType = STOCKS_LIST_TYPE.valueOf(
            intent.getStringExtra(Constants.LIST_TYPE) ?: STOCKS_LIST_TYPE.GAINERS.toString()
        )

        if (listType == STOCKS_LIST_TYPE.WATCHLIST) {
            watchListName = intent.getStringExtra(Constants.WATCHLIST_NAME)
        }

        Log.d(Constants.TAG, "debug stock list activity $listType $watchListName")

        viewModel = ViewModelProvider(this)[StockListViewModel::class.java]

        setupAppBar()
        setupRecyclerView()
        initData()
        initListeners()
    }

    private fun setupAppBar() {
        if (listType == STOCKS_LIST_TYPE.WATCHLIST) {
            binding.topAppBar.title = watchListName ?: "Watchlist"
            return
        }
        binding.topAppBar.title =
            if (listType == STOCKS_LIST_TYPE.GAINERS) "Top Gainers" else "Top Losers"
    }

    private fun initData() {
        if (listType == STOCKS_LIST_TYPE.LOSERS) {
            viewModel.fetchStocksList(STOCKS_LIST_TYPE.LOSERS)
        } else if (listType == STOCKS_LIST_TYPE.GAINERS) {
            viewModel.fetchStocksList(STOCKS_LIST_TYPE.GAINERS)
        } else if (listType == STOCKS_LIST_TYPE.WATCHLIST) {
            // ** FIX: Manually load and map watchlist data **
            val allWatchlists = SharedPreferences.readWatchlistData(this)
            val selectedWatchlist = allWatchlists.find { it.name == watchListName }

            Log.d(Constants.TAG, "read watch list data $allWatchlists \n $selectedWatchlist")

            selectedWatchlist?.let {
                // The adapter needs List<Stock>, but watchlist stores List<StockDetails>. We must map it.
                val stockList = it.stocks.map { stockDetails ->
                    Stock(
                        name = stockDetails.Symbol,
                        price = stockDetails.PERatio,
                        isGainer = Random.nextBoolean(),
                        changePercent = Random.nextDouble(0.1, 5.0)
                    )
                }
                Log.d(Constants.TAG, "stocklist map $stockList")
                (binding.recyclerView.adapter as StockAdapter).updateData(stockList)
            }
        }
    }

    private fun initListeners() {
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
        lifecycleScope.launch {
            viewModel.stockDetails.collect { data ->
                if(listType != STOCKS_LIST_TYPE.WATCHLIST){
                    Log.d(Constants.TAG,"upating adapeter 11")
                    (binding.recyclerView.adapter as StockAdapter).updateData(data)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = StockAdapter { item ->

            navigateToDetailActivity(item.name)
        }
    }

    private fun navigateToDetailActivity(stockSymbol: String) {
        val intent = Intent(this, StockDetailsActivity::class.java)
        intent.putExtra(Constants.COMPANY_NAME, stockSymbol)
        startActivity(intent)
    }
}