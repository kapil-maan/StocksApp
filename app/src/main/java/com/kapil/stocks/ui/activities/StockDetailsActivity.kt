package com.kapil.stocks.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.kapil.stocks.R
import com.kapil.stocks.constants.Constants
import com.kapil.stocks.databinding.ActivityStockDetailBinding
import com.kapil.stocks.ui.bottomsheets.WatchListChooseBottomSheet
import com.kapil.stocks.viewmodel.StockViewModel
import kotlinx.coroutines.launch

class StockDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: StockViewModel
    private lateinit var _binding: ActivityStockDetailBinding
    private lateinit var companyName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStockDetailBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(_binding.parent) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(0, statusBarInsets.top, 0, 0)
            insets
        }

        companyName = intent?.getStringExtra(Constants.COMPANY_NAME).toString()
        viewModel = ViewModelProvider(this)[StockViewModel::class.java]

        initData()
        setupListeners()
    }

    private fun initData() {
        viewModel.fetchStockDetails(companyName)
    }

    // In StockDetailsActivity.kt

    private fun setupListeners() {
        _binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.set_watchlist -> {
                    showAddToWatchlistDialog()
                    true
                }
                else -> { true }
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isVisible ->
                if (isVisible == true) {
                    _binding.progressBar.visibility = View.VISIBLE
                    _binding.stockDetailContainer.visibility = View.GONE
                } else {
                    _binding.progressBar.visibility = View.GONE
                    _binding.stockDetailContainer.visibility = View.VISIBLE
                }
            }
        }

        lifecycleScope.launch {
            viewModel.stockDetails.collect { data ->
                if (data != null) {
                    // --- EXISTING UI ELEMENTS ---
                    _binding.tvCompanyName.text = data.Name
                    _binding.tvSymbolExchange.text = "${data.Symbol}, ${data.Exchange}"
                    _binding.tvPrice.text = "$${data.PERatio}" // You might want to use a more accurate price field
                    _binding.tvDescription.text = data.Description
                    _binding.tvIndustry.text = "Industry: ${data.Industry}"
                    _binding.tvSector.text = "Sector: ${data.Sector}"
                    _binding.tvAboutHeader.text = "About ${data.Name}"
                    // You can add logic here for tvPriceChange if needed

                    // --- NEW UI ELEMENTS ---

                    // 52 Week Range
                    _binding.tv52WeekLow.text = "$${data.`52WeekLow`}"
                    _binding.tv52WeekHigh.text = "$${data.`52WeekHigh`}"

                    // Logic for the progress bar
                    val low = data.`52WeekLow`.toFloatOrNull() ?: 0f
                    val high = data.`52WeekHigh`.toFloatOrNull() ?: 0f
                    val current = data.PERatio.toFloatOrNull() ?: low // Using PE Ratio as a stand-in for price
                    if (high > low) {
                        val progress = ((current - low) / (high - low) * 100).toInt()
                        _binding.priceRangeProgressBar.progress = progress
                    }

                    // Key Statistics
                    _binding.tvMarketCapValue.text = data.MarketCapitalization
                    _binding.tvPeRatioValue.text = data.PERatio
                    _binding.tvBetaValue.text = data.Beta
                    _binding.tvDividendYieldValue.text = data.DividendYield
                    _binding.tvProfitMarginValue.text = data.ProfitMargin
                    _binding.tvEpsValue.text = data.EPS

                    setupChart()
                }
            }
        }

        _binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun showAddToWatchlistDialog() {
        val stockToAdd = viewModel.stockDetails.value
        if (stockToAdd == null) {
            Toast.makeText(this, "Stock details not loaded yet.", Toast.LENGTH_SHORT).show()
            return
        }

        val bottomsheet = WatchListChooseBottomSheet(stockToAdd)
        bottomsheet.show(supportFragmentManager, WatchListChooseBottomSheet.TAG)

//        val etNewWatchlistName = dialog.findViewById<EditText>(R.id.et_new_watchlist_name)
//        val rvExistingWatchlists = dialog.findViewById<RecyclerView>(R.id.rv_existing_watchlists)
//        val btnSave = dialog.findViewById<Button>(R.id.btn_save_to_watchlists)
//
//        val existingWatchlists = SharedPreferences.readWatchlistData(this)
//        val selectionAdapter = WatchlistSelectionAdapter(existingWatchlists)
//        rvExistingWatchlists.layoutManager = LinearLayoutManager(this)
//        rvExistingWatchlists.adapter = selectionAdapter

        // In StockDetailsActivity.kt


//        dialog.show()
    }

    private fun setupChart() {
        val entries = ArrayList<Entry>().apply {
            add(Entry(0f, 150f)); add(Entry(1f, 160f)); add(Entry(2f, 155f));
            add(Entry(3f, 165f)); add(Entry(4f, 170f)); add(Entry(5f, 175f))
        }

        val dataSet = LineDataSet(entries, "Price Trend").apply {
            color = Color.BLUE; valueTextColor = Color.BLACK; circleRadius = 4f
            lineWidth = 2f; setDrawFilled(true); fillColor = Color.CYAN
        }

        val lineData = LineData(dataSet)
        _binding.lineChart.data = lineData
        _binding.lineChart.setTouchEnabled(true); _binding.lineChart.setPinchZoom(true)
        _binding.lineChart.description = Description().apply { text = "" }
        _binding.lineChart.invalidate()
    }
}