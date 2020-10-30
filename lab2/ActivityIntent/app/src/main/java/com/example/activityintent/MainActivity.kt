package com.example.activityintent

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.counterTextView).apply {
            text = 0.toString()
        }

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val scoreView = findViewById<TextView>(R.id.scoreInputView)
            val scoreValue = scoreView.text.toString().toInt()
            val initialValue = textView.text.toString().toInt()

            val intent = Intent(this, SlaveActivity::class.java).apply {
                putExtra("com.example.activityintent", scoreValue + initialValue)
            }
            startActivityForResult(intent, 80)
        }
    }

    override fun onResume() {
        super.onResume()
        findViewById<TextView>(R.id.scoreInputView).apply {
            text = ""
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 80 && resultCode == Activity.RESULT_OK) {
            val newValue = data?.getIntExtra("com.example.activityintent", 0)
            val textView = findViewById<TextView>(R.id.counterTextView)
            textView.text = newValue.toString()
        }
    }
}