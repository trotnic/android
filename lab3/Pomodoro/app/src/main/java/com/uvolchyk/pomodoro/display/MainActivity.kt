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
    private var isFired: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, TimerService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        isServiceBound = true

        val textView = findViewById<TextView>(R.id.timerTextView)
        textView.text = "🙌"

        broadcastReceiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val ticks = intent?.getLongExtra(TimerService.TIMER_COUNTDOWN_VALUE, 0) ?: 0
                if (ticks > 0) {
                    textView.text = ticks.toString()
                } else {
                    isCounting = false
                    isFired = false
                    textView.text = "🙌"
                }
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
        menu?.getItem(0)?.isEnabled = isFired
        menu?.getItem(1)?.isEnabled = !isCounting && !isFired
        menu?.getItem(2)?.isEnabled = isCounting && isFired
        menu?.getItem(3)?.isEnabled = !isCounting && isFired
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_reset_item -> {
                val intent = Intent(this, TimerService::class.java)
                intent.putExtra(TimerService.TIMER_INTENT_TYPE, TimerService.TIMER_RESET_FLAG)
                timerService?.startService(intent)
                isCounting = false
                isFired = false
                true
            }
            R.id.menu_start_item -> {
                val intent = Intent(this, TimerService::class.java)
                intent.putExtra(TimerService.TIMER_INTENT_TYPE, TimerService.TIMER_START_FLAG)
                timerService?.startService(intent)
                isCounting = true
                isFired = true
                true
            }
            R.id.menu_pause_item -> {
                val intent = Intent(this, TimerService::class.java)
                intent.putExtra(TimerService.TIMER_INTENT_TYPE, TimerService.TIMER_PAUSE_FLAG)
                timerService?.startService(intent)
                isCounting = false
                true
            }
            R.id.menu_resume_item -> {
                val intent = Intent(this, TimerService::class.java)
                intent.putExtra(TimerService.TIMER_INTENT_TYPE, TimerService.TIMER_RESUME_FLAG)
                timerService?.startService(intent)
                isCounting = true
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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(isServiceBound) {
            unbindService(serviceConnection)
        }
    }

}