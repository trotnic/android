package com.uvolchyk.pomodoro.display

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.uvolchyk.pomodoro.R
import com.uvolchyk.pomodoro.fragments.TimeInputFragment
import com.uvolchyk.pomodoro.service.TimerService


class MainActivity : AppCompatActivity() {

    private var timerService: TimerService? = null
    private var isServiceBound: Boolean = false
    private lateinit var broadcastReceiver: BroadcastReceiver

    private var isCounting: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, TimerService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

        val textView = findViewById<TextView>(R.id.timerTextView)
        textView.text = "🙌"

//        val timerButton = findViewById<Button>(R.id.timerButton).setOnClickListener {
//            val intent = Intent(this, TimerService::class.java)
//            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
//
//        }

//        val showButton = findViewById<Button>(R.id.timerShowTime).setOnClickListener {
//            textView.text = timerService?.timestamp() ?: "Hello"
//        }

        broadcastReceiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                textView.text = if(intent?.getLongExtra("toCount", 0) ?: 0 > 0) intent?.getLongExtra("toCount", 0).toString() else "🙌"
            }
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, IntentFilter(TimerService.TIMER_BR))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.getItem(1)?.isEnabled = !isCounting
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_reset_item -> {
//                TimeInputFragment().show(supportFragmentManager, "ok")
                isCounting = false
                true
            }
            R.id.menu_start_item -> {
                timerService?.startService(Intent(this, TimerService::class.java))
                isCounting = true
                true
            }
            R.id.menu_stop_item -> {
                true
            }
            R.id.menu_resume_item -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            isServiceBound = false
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            timerService = (service as TimerService.TimerBinder).service
            isServiceBound = true
//            val intent = Intent(this@MainActivity, TimerService::class.java)
//            intent.putExtra("toCount", (123).toLong())
//            timerService?.startService(intent)
//            println("AND HERE")
        }
    }

}