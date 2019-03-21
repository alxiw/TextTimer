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
                startActivity(intent)
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {
                loadTimerCount++
            }
        }
    }

    override fun onStart() {
        super.onStart()
        timer.start()
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }
}
