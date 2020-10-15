package com.example.activityintent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

val EXTRA_VALUE = "com.example.activityintent"
var SCORE = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.counterTextView).apply {
            text = SCORE.toString()
        }

        val button = findViewById<Button>(R.id.button).setOnClickListener {
            val scoreView = findViewById<TextView>(R.id.scoreInputView)
            val scoreValue = scoreView.text.toString().toInt()
            SCORE += scoreValue
            val intent = Intent(this, SlaveActivity::class.java).apply {
                putExtra(EXTRA_VALUE, SCORE)
            }
            startActivity(intent)
        }
    }
}