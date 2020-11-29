package com.uvolchyk.pomodoro.model

class Timer {

    private var minutes: Int = 0
    private var seconds: Int = 0

    var isPause: Boolean = false
    var isCounting: Boolean = false

    var breakTimeMinutes: Int = 0
    var workTimeMinutes: Int = 0

    fun setupTimer(minutes: Int) {
        seconds = 0
        this.minutes = minutes
    }

    fun setupBreak() {
        setupTimer(breakTimeMinutes)
    }

    fun setupWork() {
        setupTimer(workTimeMinutes)
    }

    fun subtractOneSecond() {
        if (seconds == 0 && minutes > 0) {
            seconds = 60
            minutes--
        } else if (seconds == 0 && minutes == 0) {
            seconds = 60
            minutes = 60
        }
        seconds--
    }

    fun toSeconds(): Int {
        return minutes * 60 + seconds
    }

    override fun toString(): String {
        var result = ""

        val minutesString = minutes.toString()
        val secondsString = seconds.toString()

        result += (if (minutesString.length < 2) minutesString.padStart(2, '0') else minutesString) + ":"
        result += if (secondsString.length < 2) secondsString.padStart(2, '0') else secondsString

        return result
    }

    fun reset() {
        isPause = false
        isCounting = false
        setupTimer(0)
    }

}