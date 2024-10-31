package com.example.testdraw

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VM : ViewModel() {
    private val _bitmap = MutableLiveData<Bitmap>()
    val bitmap: LiveData<Bitmap> get() = _bitmap

    // Drawing variables
    private var startX = 0f
    private var startY = 0f

    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 8f
        style = Paint.Style.STROKE // Set to stroke to draw lines
    }

    init {
        // Initialize with a default bitmap
        _bitmap.value = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(_bitmap.value!!)
        val backgroundPaint = android.graphics.Paint().apply {
            color = Color.BLUE // Set initial background color
        }
        canvas.drawRect(0f, 0f, 800f, 800f, backgroundPaint)
    }

    fun draw(currentX: Float, currentY: Float) {

        // Get bitmap
        val currentBitmap = bitmap.value!!
        val canvas = android.graphics.Canvas(currentBitmap)
        Log.d("VM", "Bitmap size: ${currentBitmap.width} x ${currentBitmap.height}")

        // Draw line from the last position to the current position
        canvas.drawLine(startX, startY, currentX, currentY, paint)

        // Update start position for the next draw
        startX = currentX
        startY = currentY

        // Notify observers about the updated bitmap
        _bitmap.value = currentBitmap
    }

}