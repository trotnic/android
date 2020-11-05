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

        val initialScoreView = findViewById<TextView>(R.id.counterTextView).apply {
            text = intent.getIntExtra(MainActivity.EXTRA_KEY, 0).toString()
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            val newScoreView = findViewById<TextView>(R.id.scoreInputView)
            val newValue = newScoreView.text.toString().toInt()
            val initialValue = initialScoreView.text.toString().toInt()

            val returnIntent = Intent().apply {
                putExtra(MainActivity.EXTRA_KEY, newValue + initialValue)
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}