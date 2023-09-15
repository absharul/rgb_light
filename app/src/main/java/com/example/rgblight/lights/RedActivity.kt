package com.example.rgblight.lights

import android.animation.ValueAnimator
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import com.example.rgblight.R


class RedActivity : AppCompatActivity() {

    private var originalBrightness: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )

        // Hide the action bar (if it exists)
        supportActionBar?.hide()

        // Keep the screen on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
        }

        // Store the original screen brightness
        originalBrightness = Settings.System.getInt(
            contentResolver,
            Settings.System.SCREEN_BRIGHTNESS
        )

        // Set the screen brightness to maximum
        val contentResolver: ContentResolver = contentResolver
        Settings.System.putInt(
            contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            255 // Maximum brightness value (0-255)
        )

        // Apply the new brightness setting
        val layoutParams = window.attributes
        layoutParams.screenBrightness = 1.0f // 1.0f represents full brightness
        window.attributes = layoutParams


        setContentView(R.layout.activity_red)

        val colorView = findViewById<View>(R.id.colorViewR)
        val colorList = intArrayOf(
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.MAGENTA,
            Color.CYAN,
            // Add more colors here
        )

        val animator = ValueAnimator.ofArgb(*colorList)
        animator.addUpdateListener { animation ->
            val color = animation.animatedValue as Int
            colorView.setBackgroundColor(color)
        }
        animator.duration = 5000// Change color every 1 second
        animator.repeatCount = ValueAnimator.INFINITE // Repeat indefinitely
        animator.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Restore the original screen brightness
        if (originalBrightness != -1) {
            val contentResolver: ContentResolver = contentResolver
            Settings.System.putInt(
                contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                originalBrightness
            )

            // Apply the original brightness setting
            val layoutParams = window.attributes
            layoutParams.screenBrightness = originalBrightness.toFloat() / 255.0f
            window.attributes = layoutParams
        }
    }

}