package ru.mail.track.android.alxiw.texttimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import ru.mail.track.android.alxiw.texttimer.tools.CountTextFormatter

class TimerActivity : AppCompatActivity() {

    private lateinit var startButtonText: String
    private lateinit var stopButtonText: String

    private val maxTimerCount = 1000_000L
    private val timerCountDownInterval: Long = 1_000L

    private var timerCount: Long = 0L
    private var buttonText: String = ""

    private lateinit var timeCountFormatter: CountTextFormatter
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        startButtonText = getString(R.string.start)
        stopButtonText = getString(R.string.stop)

        timeCountFormatter = CountTextFormatter(resources)

        val button = this.findViewById<View>(R.id.button) as Button
        val textView = this.findViewById<View>(R.id.textView) as TextView

        button.setOnClickListener {
            when {
                button.text == startButtonText -> {
                    if (timerCount == maxTimerCount / timerCountDownInterval) {
                        timerCount = 0L
                    }
                    timer.start()
                    buttonText = stopButtonText
                    button.text = buttonText
                }
                button.text == stopButtonText -> {
                    timer.cancel()
                    buttonText = startButtonText
                    button.text = buttonText
                }
            }
        }

        timer = object: CountDownTimer(maxTimerCount, timerCountDownInterval) {
            override fun onFinish() {
                timer.cancel()
                buttonText = startButtonText
                button.text = buttonText
            }

            override fun onTick(millisUntilFinished: Long) {
                if (timerCount >= maxTimerCount / timerCountDownInterval) {
                    timer.onFinish()
                } else {
                    timerCount++
                    textView.text = timeCountFormatter.formatCountToText(timerCount.toInt())
                }
            }
        }

        if (savedInstanceState != null) {
            timerCount = savedInstanceState.getLong(this.getString(R.string.timer_count), 0L)
            textView.text = timeCountFormatter.formatCountToText(timerCount.toInt())
            buttonText = if (savedInstanceState.getString(this.getString(R.string.button_text), startButtonText) == "1") getString(R.string.stop) else getString(R.string.start)
            button.text = buttonText
        } else {
            buttonText = startButtonText
            button.text = buttonText
        }
    }

    override fun onStart() {
        super.onStart()

        val button = this.findViewById<View>(R.id.button) as Button
        val textView = this.findViewById<View>(R.id.textView) as TextView

        textView.text = timeCountFormatter.formatCountToText(timerCount.toInt())
        button.let {
            if (it.text == stopButtonText) {
                timer.start()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putLong(this.getString(R.string.timer_count), timerCount)
        outState?.putString(this.getString(R.string.button_text), if (buttonText == getString(R.string.stop)) "1" else "0")
        super.onSaveInstanceState(outState)
    }
}
