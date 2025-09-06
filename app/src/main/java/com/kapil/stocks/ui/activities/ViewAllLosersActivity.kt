package com.kapil.stocks.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.kapil.stocks.adapters.StockAdapter
import com.kapil.stocks.data.dummy.StockData
import com.kapil.stocks.databinding.ActivityViewAllBinding

class ViewAllLosersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewAllBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerAll.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerAll.adapter = StockAdapter(StockData.getTopLosers()) {
            // Open details if needed
        }
    }
}
