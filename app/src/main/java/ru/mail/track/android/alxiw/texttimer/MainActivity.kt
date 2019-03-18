package ru.mail.track.android.alxiw.texttimer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class MainActivity : AppCompatActivity() {

    private val maxLoadTimerCount: Long = 2_000L
    private val loadTimerCountDownInterval: Long = 1_000L

    private var loadTimerCount: Long = 0L

    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timer = object: CountDownTimer(maxLoadTimerCount, loadTimerCountDownInterval) {
            override fun onFinish() {
                val intent = Intent(this@MainActivity, TimerActivity::class.java)
                this@MainActivity.onStop()
                this@MainActivity.finish()
                startActivity(intent)
            }

            override fun onTick(millisUntilFinished: Long) {
                loadTimerCount++
            }
        }

        if (savedInstanceState != null) {
            loadTimerCount = savedInstanceState.getLong(this.getString(R.string.load_timer_count), 0L)
        }

    }

    override fun onResume() {
        super.onResume()
        if (loadTimerCount < maxLoadTimerCount) {
            timer.start()
        } else{
            timer.onFinish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putLong(this.getString(R.string.load_timer_count), loadTimerCount)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            loadTimerCount = savedInstanceState.getLong(this.getString(R.string.load_timer_count), 0L)
        }
    }
}
