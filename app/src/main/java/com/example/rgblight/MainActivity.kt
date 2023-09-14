package com.example.rgblight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import com.example.rgblight.lights.RedActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_main)


        val startButton: TextView = findViewById(R.id.btn_play)



        startButton.setOnClickListener {
            startActivity(Intent(this,RedActivity::class.java))
            finish()
        }



    }
}