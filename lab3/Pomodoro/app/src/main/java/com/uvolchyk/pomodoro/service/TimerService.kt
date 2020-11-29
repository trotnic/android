package com.uvolchyk.pomodoro.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class TimerService: Service() {

    val broadcastIntent: Intent = Intent(TIMER_BR)

    companion object {
        const val TIMER_BR = "com.uvolchyk.time.TIME_SEND"
    }

    private lateinit var timer: CountDownTimer

    inner class TimerBinder: Binder() {

        val service: TimerService
            get() = this@TimerService

    }

    override fun onCreate() {
        super.onCreate()
        timer = object: CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                broadcastIntent.putExtra("toCount", millisUntilFinished / 1000)
                LocalBroadcastManager.getInstance(this@TimerService).sendBroadcast(broadcastIntent)
            }

            override fun onFinish() {
                broadcastIntent.putExtra("toCount", -1)
                LocalBroadcastManager.getInstance(this@TimerService).sendBroadcast(broadcastIntent)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return TimerBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val secondsToCount = intent?.getLongExtra("toCount", 100) ?: 20
        timer.start()
        return super.onStartCommand(intent, flags, startId)
    }

//    fun timestamp(): String {
//        val elapsedMillis: Long = (SystemClock.elapsedRealtime() - chronometer.base)
//        val hours = (elapsedMillis / 3600000).toInt()
//        val minutes = (elapsedMillis - hours * 3600000).toInt() / 60000
//        val seconds = (elapsedMillis - hours * 3600000 - minutes * 60000).toInt() / 1000
//        val millis = (elapsedMillis - hours * 3600000 - minutes * 60000 - seconds * 1000).toInt()
//        return "$hours:$minutes:$seconds:$millis"
//    }
}
