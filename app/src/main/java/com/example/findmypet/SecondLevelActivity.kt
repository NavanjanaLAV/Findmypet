package com.example.findmypet

import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.forEach
import androidx.lifecycle.lifecycleScope
import com.example.findmypet.databinding.ActivitySecondLevelBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SecondLevelActivity : AppCompatActivity(), View.OnClickListener {
    // Declare a lateinit property for the ViewBinding instance
    private lateinit var binding: ActivitySecondLevelBinding
    private var sscore: Int = 0
    private var result: String = ""
    private var userAnswer: String = ""
    private lateinit var sharedPreferences: SharedPreferences
    private var gamesPlayed: Int = 0

    // Custom getter and setter for hhighScore, which is stored in SharedPreferences
    private var hhighScore: Int = 0
        get() = sharedPreferences.getInt("hhighScore", 0)
        set(value) {
            field = value
            sharedPreferences.edit().putInt("hhighScore", value).apply()
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // Get SharedPreferences instance
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        binding.apply {
            tvHighScore.text = getString(R.string.hhigh_score, hhighScore)
            panel1.setOnClickListener(this@SecondLevelActivity)
            panel2.setOnClickListener(this@SecondLevelActivity)
            panel3.setOnClickListener(this@SecondLevelActivity)
            panel4.setOnClickListener(this@SecondLevelActivity)
            panel5.setOnClickListener(this@SecondLevelActivity)
            panel6.setOnClickListener(this@SecondLevelActivity)
            panel7.setOnClickListener(this@SecondLevelActivity)
            panel8.setOnClickListener(this@SecondLevelActivity)
            panel9.setOnClickListener(this@SecondLevelActivity)
            panel10.setOnClickListener(this@SecondLevelActivity)
            startGame()
        }
    }


// Update the high score text view

    private fun updateUI() {
        binding.tvHighScore.text = getString(R.string.hhigh_score, hhighScore)
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
            val round = (3..5).random()
            repeat(round) {
                delay(400)
                val randomPanel = (1..10).random()
                result += randomPanel.toString()
                val panel = when (randomPanel) {
                    1 -> binding.panel1
                    2 -> binding.panel2
                    3 -> binding.panel3
                    4 -> binding.panel4
                    5 -> binding.panel5
                    6 -> binding.panel6
                    7 -> binding.panel7
                    8 -> binding.panel8
                    9 -> binding.panel9
                    else -> binding.panel10
                }
                val drawableGreen = ActivityCompat.getDrawable(this@SecondLevelActivity, R.drawable.yellow_image)
                val drawableDefault = ActivityCompat.getDrawable(this@SecondLevelActivity, R.drawable.btn_statesec)
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
            sscore = 0
            tvScore.text = "0"
            disableButtons()
            val drawableLose =
                ActivityCompat.getDrawable(this@SecondLevelActivity, R.drawable.btn_lose)
            val drawableDefault =
                ActivityCompat.getDrawable(this@SecondLevelActivity, R.drawable.btn_state)
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
                R.id.panel7 -> "9"
                R.id.panel8 -> "10"
                else ->""
            }

            // If user answer matches the result
            if(userAnswer == result)
            {
                Toast.makeText(this@SecondLevelActivity, "Good Job :)" , Toast.LENGTH_SHORT).show()
                sscore ++
                binding.tvScore.text = sscore.toString()



                if (sscore > hhighScore) {
                    hhighScore = sscore
                    sharedPreferences.edit().putInt("hhighScore", hhighScore).apply() // Save high score in SharedPreferences
                    binding.tvHighScore.text = getString(R.string.hhigh_score, hhighScore) // Update high score text view
                }
                if (sscore >= 10) { // Check if condition for second level is met
                val intent = Intent(this@SecondLevelActivity, SecondLevelCompleteActivity::class.java)
                startActivity(intent)

                } else {


                startGame() // Continue with the current level
            }
            }
            else if(userAnswer.length >= result.length)
            {
                Toast.makeText(this@SecondLevelActivity, "Game Over, Try Again" , Toast.LENGTH_SHORT).show()
                loseAnimation()
            }
        }
    }
}



