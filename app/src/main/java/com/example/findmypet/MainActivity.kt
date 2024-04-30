package com.example.findmypet

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.forEach
import androidx.lifecycle.lifecycleScope
import com.example.findmypet.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnClickListener {
    // Declare a lateinit property for the ViewBinding instance
    private lateinit var binding: ActivityMainBinding
    private var score: Int = 0
    private var result: String = ""
    private var userAnswer: String = ""
    private lateinit var sharedPreferences: SharedPreferences
    private var gamesPlayed: Int = 0

    // Custom getter and setter for highScore, which is stored in SharedPreferences
    private var highScore: Int = 0
        get() = sharedPreferences.getInt("highScore", 0)
        set(value) {
            field = value
            sharedPreferences.edit().putInt("highScore", value).apply()
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get SharedPreferences instance
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        binding.apply {
            tvHighScore.text = getString(R.string.high_score, highScore)
            panel1.setOnClickListener(this@MainActivity)
            panel2.setOnClickListener(this@MainActivity)
            panel3.setOnClickListener(this@MainActivity)
            panel4.setOnClickListener(this@MainActivity)
            panel5.setOnClickListener(this@MainActivity)
            panel6.setOnClickListener(this@MainActivity)
            panel7.setOnClickListener(this@MainActivity)
            panel8.setOnClickListener(this@MainActivity)
            startGame()
        }
    }


    // Update the high score text view
    private fun updateUI() {
        binding.tvHighScore.text = getString(R.string.high_score, highScore)
    }

    // Disable all buttons
    private fun disableButtons() {
        binding.root.forEach { view ->
            if (view is Button) {
                view.isEnabled = false

            }
        }
    }

    // Enable all buttons
    private fun enableButtons() {
        binding.root.forEach { view ->
            if (view is Button) {
                view.isEnabled = true

            }
        }
    }

    // Start a new game round
    private fun startGame() {
        result = ""
        userAnswer = ""
        disableButtons()
        lifecycleScope.launch {
            val round = (2..4).random()
            repeat(round) {
                delay(400)
                val randomPanel = (1..8).random()
                result += randomPanel.toString()
                val panel = when (randomPanel) {
                    1 -> binding.panel1
                    2 -> binding.panel2
                    3 -> binding.panel3
                    4 -> binding.panel4
                    5 -> binding.panel5
                    6 -> binding.panel6
                    7 -> binding.panel7
                    else -> binding.panel8
                }
                val drawableGreen = ActivityCompat.getDrawable(this@MainActivity, R.drawable.green_image)
                val drawableDefault = ActivityCompat.getDrawable(this@MainActivity, R.drawable.btn_state)
                panel.background = drawableGreen
                delay(1000)
                panel.background = drawableDefault
            }
            enableButtons()



        }

    }

    // Animation for losing the game

    private fun loseAnimation() {
        binding.apply{
            score = 0
            tvScore.text = "0"
            disableButtons()
            val drawableLose =
                ActivityCompat.getDrawable(this@MainActivity, R.drawable.btn_lose)
            val drawableDefault =
                ActivityCompat.getDrawable(this@MainActivity, R.drawable.btn_state)
            lifecycleScope.launch {
                binding.root.forEach { view ->
                    if (view is Button)
                    {
                        view.background = drawableLose
                        delay(300)
                        view.background = drawableDefault
                    }

                }
                delay(1000)
                startGame()
            }

        }
    }

    // Handle button clicks
    override fun onClick(view: View?) {
        view?.let{
            userAnswer += when(it.id)
            {
                R.id.panel1 -> "1"
                R.id.panel2 -> "2"
                R.id.panel3 -> "3"
                R.id.panel4 -> "4"
                R.id.panel5 -> "5"
                R.id.panel6 -> "6"
                R.id.panel7 -> "7"
                R.id.panel8 -> "8"
                else ->""
            }


            // If user answer matches the result
            if(userAnswer == result)
            {
                Toast.makeText(this@MainActivity, "congratulation :)" , Toast.LENGTH_SHORT).show()
                score ++
                binding.tvScore.text = score.toString()


                // Update high score if necessar
                if (score > highScore) {
                    highScore = score
                    sharedPreferences.edit().putInt("highScore", highScore).apply() // Save high score in SharedPreferences
                    binding.tvHighScore.text = getString(R.string.high_score, highScore) // Update high score text view
                }

                // If score meets the condition for second level
                if (score >= 10) { // Check if condition for second level is met
                val intent = Intent(this@MainActivity, SecondLevelStartActivity::class.java)
                startActivity(intent)


            } else {
                startGame() // Continue with the current level
            }
            }
            else if(userAnswer.length >= result.length)
            {
                // If user answer is incorrect and has reached the length of the result
                Toast.makeText(this@MainActivity, "Game Over, Try Again" , Toast.LENGTH_SHORT).show()
                loseAnimation()
            }
        }
    }
}



