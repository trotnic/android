package com.example.activityintent

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    companion object Constant {
        const val EXTRA_KEY = "com.example.activityintent"
        const val REQUEST_CODE = 80
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val initialScoreView = findViewById<TextView>(R.id.counterTextView).apply {
            text = 0.toString()
        }

        findViewById<Button>(R.id.button).setOnClickListener {

            val newScoreView = findViewById<TextView>(R.id.scoreInputView)
            val newValue = newScoreView.text.toString()

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
                val initialValue = initialScoreView.text.toString().toInt()

                val intent = Intent(this, SlaveActivity::class.java).apply {
                    putExtra(EXTRA_KEY, newValue.toInt() + initialValue)
                }
                startActivityForResult(intent, REQUEST_CODE)
            }
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