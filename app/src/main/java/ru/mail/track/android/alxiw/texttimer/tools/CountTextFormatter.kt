package ru.mail.track.android.alxiw.texttimer.tools

class CountTextFormatter(
    private val units: Array<String>,
    private val teens: Array<String>,
    private val tens: Array<String>,
    private val hundreds: Array<String>,
    private val thousands: Array<String>
) {

    private val separator = " "

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