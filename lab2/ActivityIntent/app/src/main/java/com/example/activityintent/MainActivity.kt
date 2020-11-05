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

    companion object Constant {
        val EXTRA_KEY = "com.example.activityintent"
        val REQUEST_CODE = 80
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val initialScoreView = findViewById<TextView>(R.id.counterTextView).apply {
            text = 0.toString()
        }

        val confirmButton = findViewById<Button>(R.id.button)
        confirmButton.setOnClickListener {
            val newScoreView = findViewById<TextView>(R.id.scoreInputView)
            val newValue = newScoreView.text.toString().toInt()
            val initialValue = initialScoreView.text.toString().toInt()

            val intent = Intent(this, SlaveActivity::class.java).apply {
                putExtra(EXTRA_KEY, newValue + initialValue)
            }
            startActivityForResult(intent, REQUEST_CODE)
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

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val newValue = data?.getIntExtra(EXTRA_KEY, 0)
            val textView = findViewById<TextView>(R.id.counterTextView)
            textView.text = newValue.toString()
        }
    }
}