package com.kapil.stocks.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kapil.stocks.databinding.ActivityAddToWatchlistBinding

class AddToWatchlistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddToWatchlistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddToWatchlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val existingLists = listOf("Tech Stocks", "Favorites", "Watch Later")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, existingLists)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerExistingWatchlists.adapter = adapter

        binding.btnAdd.setOnClickListener {
            val selected = binding.spinnerExistingWatchlists.selectedItem.toString()
            Toast.makeText(this, "Added to $selected", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnCreateNew.setOnClickListener {
            val newListName = binding.editNewWatchlist.text.toString().trim()
            if (newListName.isNotEmpty()) {
                Toast.makeText(this, "Created and added to $newListName", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Enter watchlist name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
