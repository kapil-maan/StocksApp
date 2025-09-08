package com.kapil.stocks.adapters

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kapil.stocks.R
import com.kapil.stocks.constants.Constants
import com.kapil.stocks.data.model.Stock
import kotlin.math.abs

class StockAdapter(private val onClick: (Stock) -> Unit) :
    RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    private var _data: List<Stock> = emptyList()

    inner class StockViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.stockName)
        val price: TextView = view.findViewById(R.id.stockPrice)
        val logo: ImageView = view.findViewById(R.id.stockLogo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = _data[position]
        holder.name.text = stock.name
        holder.price.text = stock.price
        holder.itemView.setOnClickListener { onClick(stock) }

        // Create the letter icon bitmap and set it on the ImageView
        val letterBitmap = createLetterBitmap(stock.name)
        holder.logo.setImageBitmap(letterBitmap)
    }

    fun updateData(list: List<Stock>){
        _data = list
        Log.d(Constants.TAG, "bro in adpater $list")
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = _data.size

    /**
     * Creates a circular Bitmap with the first letter of a given text.
     * This function requires no external libraries.
     */
    private fun createLetterBitmap(text: String): Bitmap {
        val width = 100
        val height = 100
        val firstLetter = text.firstOrNull()?.toString()?.uppercase() ?: "#"

        // Create a blank bitmap
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // --- Set up Paint for drawing ---
        val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        // Generate a color from the text hash code
        val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN, Color.GRAY)
        backgroundPaint.color = colors[abs(text.hashCode()) % colors.size]

        textPaint.color = Color.WHITE
        textPaint.textSize = (width / 2).toFloat() // Text size is half the bitmap width
        textPaint.textAlign = Paint.Align.CENTER

        // --- Draw the circle and text ---
        val centerX = (width / 2).toFloat()
        val centerY = (height / 2).toFloat()
        val radius = (width / 2).toFloat()

        // Draw the colored circle
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint)

        // Draw the centered letter
        // This calculation centers the text vertically
        val textY = centerY - (textPaint.descent() + textPaint.ascent()) / 2
        canvas.drawText(firstLetter, centerX, textY, textPaint)

        return bitmap
    }
}