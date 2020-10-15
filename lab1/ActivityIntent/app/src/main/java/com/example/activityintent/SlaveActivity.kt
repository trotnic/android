package com.example.activityintent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SlaveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scoreView = findViewById<TextView>(R.id.counterTextView).apply {
            text = intent.getIntExtra(EXTRA_VALUE, 12).toString()
        }

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val scoreView = findViewById<TextView>(R.id.scoreInputView)
            val scoreValue = scoreView.text.toString().toInt()
            SCORE += scoreValue
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(EXTRA_VALUE, SCORE)
            }
            startActivity(intent)
        }
    }
}