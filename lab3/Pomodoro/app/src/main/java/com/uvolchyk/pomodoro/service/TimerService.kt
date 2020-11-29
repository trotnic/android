package com.uvolchyk.pomodoro.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class TimerService: Service() {

    val broadcastIntent: Intent = Intent(TIMER_BR)

    private var timeLeft: Long = 31000

    companion object {
        const val TIMER_BR = "com.uvolchyk.time.TIME_SEND"
        const val TIMER_FAULT_FLAG = -1
        const val TIMER_START_FLAG = 0
        const val TIMER_PAUSE_FLAG = 1
        const val TIMER_RESUME_FLAG = 2
        const val TIMER_RESET_FLAG = 3
        const val TIMER_INTENT_TYPE = "com.uvolchyk.time.TYPE"

        const val TIMER_COUNTDOWN_VALUE = "toCount"
    }

    private lateinit var timer: CountDownTimer

    inner class TimerBinder: Binder() {
        val service: TimerService
            get() = this@TimerService
    }

    override fun onCreate() {
        super.onCreate()
        timer = setupTimer(timeLeft)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return TimerBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.getIntExtra(TIMER_INTENT_TYPE, TIMER_FAULT_FLAG) ?: TIMER_FAULT_FLAG) {
            TIMER_START_FLAG -> {
                timer.start()
            }
            TIMER_PAUSE_FLAG -> {
                println(timeLeft)
                timer.cancel()
            }
            TIMER_RESUME_FLAG -> {
                timer = setupTimer(timeLeft).start()
            }
            TIMER_RESET_FLAG -> {
                timer.cancel()
                timer.onFinish()
            }
            TIMER_FAULT_FLAG -> {
                println("Ooops, i did it again... 🙃")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun setupTimer(time: Long): CountDownTimer {
        return object: CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                broadcastIntent.putExtra(TIMER_COUNTDOWN_VALUE, millisUntilFinished / 1000)
                LocalBroadcastManager.getInstance(this@TimerService).sendBroadcast(broadcastIntent)
            }

            override fun onFinish() {
                broadcastIntent.putExtra(TIMER_COUNTDOWN_VALUE, -1)
                LocalBroadcastManager.getInstance(this@TimerService).sendBroadcast(broadcastIntent)
            }
        }
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
