package com.kapil.stocks.ui.bottomsheets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kapil.stocks.adapters.WatchlistSelectionAdapter
import com.kapil.stocks.data.model.StockDetails
import com.kapil.stocks.data.model.WatchList
import com.kapil.stocks.databinding.DialogAddToWatchlistBinding
import com.kapil.stocks.services.SharedPreferences
import java.util.Locale

class WatchListChooseBottomSheet(val stockToAdd: StockDetails) : BottomSheetDialogFragment() {

    private var watchlists: List<WatchList> = emptyList()

    private lateinit var binding: DialogAddToWatchlistBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddToWatchlistBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initListeners()
        initData()
    }

    private fun initData() {
        watchlists = SharedPreferences.readWatchlistData(this.requireContext())
        (binding.rvExistingWatchlists.adapter as WatchlistSelectionAdapter).updateData(watchlists)
    }

    private fun setupRecyclerView() {
        binding.rvExistingWatchlists.layoutManager = LinearLayoutManager(this.context)
        binding.rvExistingWatchlists.adapter = WatchlistSelectionAdapter()
    }

    fun initListeners(){
        binding.createNewWatchlist.setOnClickListener {
            val newName = binding.etNewWatchlistName.text.toString().trim()
            if(watchlists.find { it.name.toLowerCase() == newName.toLowerCase() } == null){
                watchlists += WatchList(newName, emptyList())
                (binding.rvExistingWatchlists.adapter as WatchlistSelectionAdapter).updateData(watchlists)
                binding.etNewWatchlistName.text.clear()
            } else {
                Toast.makeText(this.context, "WatchList already exists", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnSaveToWatchlists.setOnClickListener {
            val selectedNames = (binding.rvExistingWatchlists.adapter as WatchlistSelectionAdapter).selectedWatchlistNames

            if (selectedNames.isEmpty()) {
                Toast.makeText(this.context, "Please select or create a watchlist.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(this.context == null) return@setOnClickListener;

            val watchlistsMap = SharedPreferences.readWatchlistData(this.requireContext())
                .associateBy { it.name.lowercase(Locale.ROOT) }
                .toMutableMap()

            for (targetName in selectedNames) {
                val key = targetName.lowercase(Locale.ROOT)
                val existingList = watchlistsMap[key]

                if (existingList != null) {
                    // --- ⭐ THIS IS THE CORRECTED LOGIC ⭐ ---
                    val stockAlreadyExists = existingList.stocks.any { it.Symbol == stockToAdd.Symbol }
                    if (!stockAlreadyExists) {
                        // Only update the list IF the stock is NOT already in it
                        val updatedList = existingList.copy(stocks = existingList.stocks + stockToAdd)
                        watchlistsMap[key] = updatedList
                    }
                    // If the stock already exists, we do nothing, preventing duplicates.

                } else {
                    // NEW WATCHLIST: Create it and add it to the map.
                    val newList = WatchList(name = targetName, stocks = listOf(stockToAdd))
                    watchlistsMap[key] = newList
                }
            }

            val finalListToSave = watchlistsMap.values.toList()

            // (Optional but Recommended) Add the debug log from the previous step to always check what you're saving
            Log.d("WATCHLIST_DEBUG", "--- FINAL LIST TO SAVE ---")
            finalListToSave.forEach { watchlist ->
                Log.d("WATCHLIST_DEBUG", "Name: ${watchlist.name}, Stocks: ${watchlist.stocks.map { it.Symbol }.distinct()}") // Using distinct() for cleaner logging
            }

            Log.d("WATCHLIST_DEBUG","WATCHLIST_DEBUG final map $finalListToSave")

            SharedPreferences.saveWatchlistData(this.requireContext(), finalListToSave)

            Toast.makeText(this.context, "Saved to watchlist(s)!", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }


}