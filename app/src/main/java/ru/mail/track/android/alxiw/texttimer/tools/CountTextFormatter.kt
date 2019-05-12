package ru.mail.track.android.alxiw.texttimer.tools

import android.content.res.Resources
import ru.mail.track.android.alxiw.texttimer.R

class CountTextFormatter(resources: Resources) {

    private val units: Array<String> = resources.getStringArray(R.array.units)
    private val teens: Array<String> = resources.getStringArray(R.array.teens)
    private val tens: Array<String> = resources.getStringArray(R.array.tens)
    private val hundreds: Array<String> = resources.getStringArray(R.array.hundreds)
    private val thousands: Array<String> = resources.getStringArray(R.array.thousands)

    fun formatCountToText(timerCount: Int): String {
        var result = ""

        if (timerCount > 1000) {
            return "∞"
        }

        if (timerCount / 1000 != 0) {
            result += thousands[timerCount / 1000 - 1]
        }

        if (timerCount / 100 != 0) {
            result += hundreds[timerCount / 100 - 1]
        }

        if (timerCount / 10 % 10 > 1) {
            if (result.isNotEmpty()) result = "$result "
            result += tens[timerCount / 10 % 10 - 1]
        }

        if (timerCount / 10 % 10 == 1) {
            if (result.isNotEmpty()) result = "$result "
            result += if (timerCount % 10 == 0) tens[0] else teens[timerCount % 10 - 1]
        }

        if (timerCount % 10 != 0 && timerCount / 10 % 10 != 1) {
            if (result.isNotEmpty()) result = "$result "
            result += units[timerCount % 10 - 1]
        }

        return result
    }
}