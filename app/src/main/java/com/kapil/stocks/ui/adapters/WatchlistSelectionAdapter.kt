package com.kapil.stocks.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.kapil.stocks.R
import com.kapil.stocks.constants.Constants
import com.kapil.stocks.data.model.WatchList

class WatchlistSelectionAdapter() :
    RecyclerView.Adapter<WatchlistSelectionAdapter.ViewHolder>() {

    private var _data: List<WatchList> = emptyList()
    val selectedWatchlistNames = mutableSetOf<String>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Ensure this ID matches the CheckBox in your item_watchlist_selectable.xml
        val checkBox: CheckBox = view.findViewById(R.id.cb_watchlist_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_watchlist_selectable, parent, false)
        return ViewHolder(view)
    }

    fun updateData(list: List<WatchList>) {
        _data = list
        Log.d(Constants.TAG, "bro in adpater $list")
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val watchlist = _data[position]
        holder.checkBox.text = watchlist.name

        // --- THE FIX IS HERE ---

        // 1. Temporarily remove the listener to prevent it from firing accidentally.
        holder.checkBox.setOnCheckedChangeListener(null)

        // 2. Set the correct checked state based on our list of selected names.
        holder.checkBox.isChecked = selectedWatchlistNames.contains(watchlist.name)

        // 3. Re-attach the listener so it works when the user actually clicks it.
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedWatchlistNames.add(watchlist.name)
            } else {
                selectedWatchlistNames.remove(watchlist.name)
            }
        }
    }

    override fun getItemCount() = _data.size
}