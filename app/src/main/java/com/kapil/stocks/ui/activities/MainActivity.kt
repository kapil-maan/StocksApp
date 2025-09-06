package com.kapil.stocks.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.kapil.stocks.R
import com.kapil.stocks.data.dummy.StockData
import com.kapil.stocks.adapters.StockAdapter
import com.kapil.stocks.databinding.ActivityMainBinding

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
        // Show only 4 items for preview (scrollable list within ScrollView)
        val gainers = StockData.getTopGainers().take(4)
        val losers = StockData.getTopLosers().take(4)

        binding.recyclerGainers.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerGainers.adapter = StockAdapter(gainers) {
            // TODO: navigate to StockDetailsActivity
        }

        binding.recyclerLosers.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerLosers.adapter = StockAdapter(losers) {
            // TODO: navigate to StockDetailsActivity
        }
    }

    private fun setupBottomNav() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> true // already on home
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
            startActivity(Intent(this, ViewAllGainersActivity::class.java))
        }

        binding.viewAllLosers.setOnClickListener {
            startActivity(Intent(this, ViewAllLosersActivity::class.java))
        }
    }
}
