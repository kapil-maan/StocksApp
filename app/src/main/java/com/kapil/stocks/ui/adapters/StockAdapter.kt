package com.kapil.stocks.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kapil.stocks.R
import com.kapil.stocks.data.model.Stock

class StockAdapter(private val list: List<Stock>, private val onClick: (Stock) -> Unit) :
    RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    inner class StockViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.stockName)
        val price: TextView = view.findViewById(R.id.stockPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = list[position]
        holder.name.text = stock.name
        holder.price.text = stock.price
        holder.itemView.setOnClickListener { onClick(stock) }
    }

    override fun getItemCount(): Int = list.size
}