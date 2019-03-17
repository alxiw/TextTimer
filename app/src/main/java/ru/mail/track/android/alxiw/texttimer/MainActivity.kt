package ru.mail.track.android.alxiw.texttimer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle

class MainActivity : AppCompatActivity() {

    private val maxLoadTimerCount: Long = 2_000L
    private val loadTimerCountDownInterval: Long = 1_000L

    private var loadTimerCount: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        object: CountDownTimer(maxLoadTimerCount, loadTimerCountDownInterval) {
            override fun onFinish() {
                val intent = Intent(this@MainActivity, TimerActivity::class.java)
                this@MainActivity.finish()
                startActivity(intent)
            }

            override fun onTick(millisUntilFinished: Long) {
                loadTimerCount++
            }

        }.start()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putLong(this.getString(R.string.load_timer_count), loadTimerCount)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            loadTimerCount = savedInstanceState.getLong(this.getString(R.string.load_timer_count))
        }
    }
}
