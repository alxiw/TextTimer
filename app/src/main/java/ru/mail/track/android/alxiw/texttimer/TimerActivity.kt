package ru.mail.track.android.alxiw.texttimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_timer.button
import kotlinx.android.synthetic.main.activity_timer.textView
import ru.mail.track.android.alxiw.texttimer.tools.CountTextFormatter

class TimerActivity : AppCompatActivity() {

    private val startButtonText: String = "START"
    private val stopButtonText: String = "STOP"

    private val maxTimerCount = 1_000_000L
    private val timerCountDownInterval: Long = 1_000L

    private var timerCount: Long = 0L
    private var buttonText: String = ""

    private lateinit var timeCountFormatter: CountTextFormatter
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        timeCountFormatter = CountTextFormatter(
            resources.getStringArray(R.array.units),
            resources.getStringArray(R.array.teens),
            resources.getStringArray(R.array.tens),
            resources.getStringArray(R.array.hundreds),
            resources.getStringArray(R.array.thousands)
            )

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
            buttonText = savedInstanceState.getString(this.getString(R.string.button_text), startButtonText)
            button.text = buttonText
        } else {
            buttonText = startButtonText
            button.text = buttonText
        }
    }

    override fun onStart() {
        super.onStart()
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
        outState?.putString(this.getString(R.string.button_text), buttonText)
        super.onSaveInstanceState(outState)
    }
}
