package com.example.activityintent

import android.app.Activity
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
            text = intent.getIntExtra("com.example.activityintent", 0).toString()
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            val scoreView = findViewById<TextView>(R.id.scoreInputView)
            val scoreValue = scoreView.text.toString().toInt()
            val initialValue = scoreView.text.toString().toInt()

            val returnIntent = Intent().apply {
                putExtra("com.example.activityintent", scoreValue + initialValue)
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}