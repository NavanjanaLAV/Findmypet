package com.example.findmypet

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.findmypet.databinding.ActivitySecondLevelStartBinding

class SecondLevelStartActivity : AppCompatActivity() {
    // Declare a lateinit property for the ViewBinding instance
    private lateinit var binding: ActivitySecondLevelStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySecondLevelStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set a click listener on the "Enter" button
        binding.btnEnter.setOnClickListener {
            navigateToSecondLevel()
        }
    }


    // Function to navigate to the SecondLevelActivity
    private fun navigateToSecondLevel() {
        // Create an Intent to start the SecondLevelActivity
        val intent = Intent(this, SecondLevelActivity::class.java)
        startActivity(intent)
        finish()
    }
}
