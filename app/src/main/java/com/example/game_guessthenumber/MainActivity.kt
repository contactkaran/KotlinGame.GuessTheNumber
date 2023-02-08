package com.example.game_guessthenumber

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.game_guessthenumber.databinding.ActivityMainBinding
import kotlinx.coroutines.internal.artificialFrame
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var answer = 0
    var isGameOver = false
    var numOfAttempts = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startOver()

    }

    //arranging functions in alphabetical order, as per convention

    fun generateAnswer() {
        //generates random number between 1 and 25
        answer = Random.nextInt(1, 25)
    }

    fun startOver() {
        isGameOver = false
        generateAnswer()
        numOfAttempts = 0

        val answerTextView = findViewById<TextView>(R.id.answer)
        answerTextView.text = "??"

        val submitButton = findViewById<Button>(R.id.buttonSubmit)
        submitButton.isEnabled = true

        val textView = findViewById<TextView>(R.id.textView)
        textView.text = "Guess 1 to 25"

        val editTextGuess = findViewById<EditText>(R.id.editTextGuess)
        editTextGuess.text.clear()
    }

    fun btnStartOverTapped(view: View){
        //passing the argument that is a "button" , which is a View
        startOver()
    }

    @SuppressLint("SetTextI18n")
    fun btnSubmitTapped(view: View){
        //elvis operator (notice the hairlock looking at it sideways?), when user input value is null, you get int value as -99
        val guess = getUserGuess() ?: -99

        val textView = findViewById<TextView>(R.id.textView)


        //check if input value is not between specified range
        if(guess !in 1..25){
            textView.text = "Guess must be between 1 to 25"
            Toast.makeText(this, "Please add a number between 1 and 25", Toast.LENGTH_SHORT).show()
            return
        }

        var message = ""
        numOfAttempts++

        if(guess == answer){
            message = "Correct! Guess(es): $numOfAttempts"
            isGameOver = true

            val answerTextView = findViewById<TextView>(R.id.answer)
            //accessing the "text" property of the object "answer"
            answerTextView.text = answer.toString()

            val submitButton = findViewById<Button>(R.id.buttonSubmit)
            submitButton.isEnabled = false

        }
        else{
            //using ternary operator to work with two possible outcomes
            message = if(guess < answer) "Too low!" else "Too high!"
        }

        textView.text = message

    }
    fun getUserGuess(): Int? {
        val editTextGuess = findViewById<EditText>(R.id.editTextGuess)
        val userGuess = editTextGuess.text.toString()
        //val guessAsInt = userGuess.toInt()
        var guessAsInt = 0
        try {
            guessAsInt = userGuess.toInt()
        }
        catch (e: Exception){
            return null
        }

        return guessAsInt
    }

}