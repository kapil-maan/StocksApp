package com.kapil.stocks.ui.activities

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kapil.stocks.R
import com.kapil.stocks.data.model.Stock

class StockDetailsActivity : AppCompatActivity() {

    private lateinit var stockNameTextView: TextView
    private lateinit var stockPriceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_details)

        stockNameTextView = findViewById(R.id.textStockName)
        stockPriceTextView = findViewById(R.id.textStockPrice)

        // Safely receive stock data
        @Suppress("DEPRECATION")
        val stock: Stock? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("stock", Stock::class.java)
        } else {
            intent.getSerializableExtra("stock") as? Stock
        }

        if (stock != null) {
            stockNameTextView.text = stock.name
            stockPriceTextView.text = stock.price
        } else {
            Toast.makeText(this, "Stock data not found!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
