package com.example.testdraw

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue

import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput

class MainActivity : ComponentActivity() {
    val myViewModel: VM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BitmapDisplay(myViewModel)
        }
    }
}

@Composable
fun BitmapDisplay(myViewModel: VM, modifier: Modifier = Modifier) {
    val bitmap by myViewModel.bitmap.observeAsState(initial = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888))

    Canvas(modifier = modifier
        .fillMaxSize() // Ensure it fills the screen
        .background(Color.White)
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                Log.e("BitmapDisplay", "Touch detected at: ${change.position}")
                change.consume()

                // Draw on the bitmap using the view model
                myViewModel.draw(change.position.x, change.position.y)
            }
        }
    ) {
        Log.e("BitmapDisplay", "drawing bitmap")
        drawImage(bitmap.asImageBitmap(), topLeft = androidx.compose.ui.geometry.Offset(0f, 0f))
    }
}