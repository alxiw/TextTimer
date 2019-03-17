package ru.mail.track.android.alxiw.texttimer.tools

import android.content.res.Resources

class CountTextFormatter(resources: Resources) {

    private val separator = " "

    private val units = resources.getStringArray(ru.mail.track.android.alxiw.texttimer.R.array.units)
    private val teens = resources.getStringArray(ru.mail.track.android.alxiw.texttimer.R.array.teens)
    private val tens = resources.getStringArray(ru.mail.track.android.alxiw.texttimer.R.array.tens)
    private val hundreds = resources.getStringArray(ru.mail.track.android.alxiw.texttimer.R.array.hundreds)
    private val thousands = resources.getStringArray(ru.mail.track.android.alxiw.texttimer.R.array.thousands)

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
            if (result.isNotEmpty()) result += separator
            result += tens[timerCount / 10 % 10 - 1]
        }

        if (timerCount / 10 % 10 == 1) {
            if (result.isNotEmpty()) result += separator
            result += if (timerCount % 10 == 0) tens[0] else teens[timerCount % 10 - 1]
        }

        if (timerCount % 10 != 0 && timerCount / 10 % 10 != 1) {
            if (result.isNotEmpty()) result += separator
            result += units[timerCount % 10 - 1]
        }

        return result
    }

}