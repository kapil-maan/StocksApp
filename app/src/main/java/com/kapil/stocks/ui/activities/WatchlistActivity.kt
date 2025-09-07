package com.kapil.stocks.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.kapil.stocks.adapters.WatchlistAdapter
import com.kapil.stocks.constants.Constants
import com.kapil.stocks.databinding.ActivityWatchlistBinding
import com.kapil.stocks.services.SharedPreferences
import com.kapil.stocks.viewmodel.STOCKS_LIST_TYPE

class WatchlistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWatchlistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.parent) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(0, statusBarInsets.top, 0, 0)
            insets
        }

        setupRecyclerView()
        initListeners()
    }

    override fun onResume() {
        super.onResume()

        initData()
    }

    private fun initListeners() {
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initData() {
        val watchListData = SharedPreferences.readWatchlistData(this)
        Log.d(Constants.TAG, "watchlist data ${watchListData}")
        if (watchListData.isEmpty()) {
            binding.watchlistNotPresent.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.watchlistNotPresent.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            (binding.recyclerView.adapter as WatchlistAdapter).updateData(
                watchListData
            )
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.adapter = WatchlistAdapter { selectedWatchlist ->
            navigateToListActivity(selectedWatchlist.name)
        }
    }


    private fun navigateToListActivity(watchListName: String) {
        val intent = Intent(this, StockListActivity::class.java)
        intent.putExtra(Constants.LIST_TYPE, STOCKS_LIST_TYPE.WATCHLIST.toString())

        intent.putExtra(Constants.WATCHLIST_NAME, watchListName)
        startActivity(intent)
    }
}