package com.kapil.stocks.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.kapil.stocks.R
import com.kapil.stocks.adapters.StockAdapter
import com.kapil.stocks.data.dummy.StockData
import com.kapil.stocks.databinding.ActivityMainBinding
import com.kapil.stocks.ui.activities.StockDetailsActivity
import com.kapil.stocks.ui.activities.ViewAllGainersActivity
import com.kapil.stocks.ui.activities.ViewAllLosersActivity
import com.kapil.stocks.ui.activities.WatchlistActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        setupBottomNav()
        setupViewAllClickListeners()
    }

    private fun setupRecyclerViews() {
        val gainers = StockData.getTopGainers().take(4)
        val losers = StockData.getTopLosers().take(4)

        binding.recyclerGainers.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerGainers.adapter = StockAdapter(gainers) { stock ->
            openStockDetails(stock.name)  // name == symbol
        }

        binding.recyclerLosers.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerLosers.adapter = StockAdapter(losers) { stock ->
            openStockDetails(stock.name)
        }
    }

    private fun openStockDetails(symbol: String) {
        val intent = Intent(this, StockDetailsActivity::class.java)
        intent.putExtra("stock_symbol", symbol)
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

//    private fun setupViewAllClickListeners() {
//        binding.viewAllGainers.setOnClickListener {
//            startActivity(Intent(this, ViewAllGainersActivity::class.java))
//        }
//
//        binding.viewAllLosers.setOnClickListener {
//            startActivity(Intent(this, ViewAllLosersActivity::class.java))
//        }
//    }
private fun setupViewAllClickListeners() {
    binding.viewAllGainers.setOnClickListener {
        val intent = Intent(this, ViewAllStocksActivity::class.java)
        intent.putExtra("isGainers", true)
        startActivity(intent)
    }

    binding.viewAllLosers.setOnClickListener {
        val intent = Intent(this, ViewAllStocksActivity::class.java)
        intent.putExtra("isGainers", false)
        startActivity(intent)
    }
}



}
