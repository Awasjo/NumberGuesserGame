package com.awas.awas_final

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.awas.awas_final.database.Score
import com.awas.awas_final.database.ScoreRepository
import com.awas.awas_final.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val TAG = this.javaClass.canonicalName
    private var randomNumber : Int? = null
    private var remainingAttempts = 5
    private var winner : Boolean = false

    private lateinit var scoreRepository: ScoreRepository



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)


        scoreRepository = ScoreRepository(application = this.application)


        //random number generator
        randomNumber = Random.nextInt(1, 26)
        Log.d(TAG, "random number: $randomNumber")

        binding.attemptsTextView.text = "Remaining Attempts: $remainingAttempts"
        binding.checkButton.setOnClickListener {
            checkGuess()
        }
        binding.scoreButton.setOnClickListener {
            goToScoreBoardActivity()
        }
    }

    private fun checkGuess() {
        val userGuess = binding.guessEditText.text.toString().toIntOrNull()

        remainingAttempts--
        binding.attemptsTextView.text = "Attempts remaining: $remainingAttempts"

        if (userGuess == randomNumber) {
            showAlertDialog("Congratulations!", "You guessed correctly. Play again?", true)
            winner = true
            saveNoteToDB()
        } else {
            if (remainingAttempts > 0) {
                binding.attemptsTextView.text = "Wrong guess. $remainingAttempts attempts remaining."
            } else if(remainingAttempts <= 0){
                showAlertDialog("Game Over", "Sorry, you're out of attempts. The correct number was $randomNumber. Play again?", true)
                winner = false
                saveNoteToDB()
            }
        }

        if (userGuess == null) {
            binding.userNumberTextView.text = "Please enter a valid number."
            return
        }

        if (userGuess < 1 || userGuess > 25) {
            binding.userNumberTextView.text = "Please enter a number between 1 and 25."
            return
        }

        if (userGuess < randomNumber!!) {
            showToast("The number you chose is less than the answer")
            return
        }

        if (userGuess > randomNumber!!) {
            showToast("The number you chose is greater than the answer")
            return
        }
    }


    private fun showAlertDialog(title: String, message: String, playAgain: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Play Again") { _, _ ->
                if (playAgain) {
                    restartGame()
                }
            }
            .setNegativeButton("Exit") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }

    private fun restartGame() {
        binding.guessEditText.text.clear()
        remainingAttempts = 5
        randomNumber = Random.nextInt(1, 26)
        Log.d(TAG, "random number: $randomNumber")
        binding.guessEditText.isEnabled = true
        binding.scoreButton.isEnabled = true
        binding.userNumberTextView.text = " "
        binding.attemptsTextView.text= " "
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveNoteToDB(){

        val newScore = Score("awas_jomail", winner)

        //insert note to DB
        lifecycleScope.launch { scoreRepository.insertNote(newScore) }

        //display the toast and go back to previous screen
        Toast.makeText(this, "Score added to DB", Toast.LENGTH_SHORT).show()
    }

    private fun goToScoreBoardActivity() {
        val intent = Intent(this, ScoreActivity::class.java)
        startActivity(intent)
    }


}