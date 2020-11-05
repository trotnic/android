package com.example.activityintent

import android.app.Activity
import android.app.AlertDialog
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
            val newValue = newScoreView.text.toString()
            val initialValue = initialScoreView.text.toString().toInt()

            if (newValue == "") {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.warning_title)
                builder.setMessage(R.string.warning_message)
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setPositiveButton(R.string.warning_neutral){ _, _ -> }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            } else {
                val returnIntent = Intent().apply {
                    putExtra(MainActivity.EXTRA_KEY, newValue.toInt() + initialValue)
                }
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }
}