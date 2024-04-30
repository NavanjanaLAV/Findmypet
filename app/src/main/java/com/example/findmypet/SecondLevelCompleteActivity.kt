package com.example.findmypet

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondLevelCompleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge rendering for the activity
        enableEdgeToEdge()


        // Set the content view of the activity to the layout file 'activity_second_level_complete.xml'
        setContentView(R.layout.activity_second_level_complete)

    }
}