package ru.mail.track.android.alxiw.texttimer.tools

import android.content.res.Resources
import ru.mail.track.android.alxiw.texttimer.R
import java.util.*

class CountTextFormatter(resources: Resources) {

    private val units: Array<String> = resources.getStringArray(R.array.units)
    private val teens: Array<String> = resources.getStringArray(R.array.teens)
    private val tens: Array<String> = resources.getStringArray(R.array.tens)
    private val hundreds: Array<String> = resources.getStringArray(R.array.hundreds)
    private val thousands: Array<String> = resources.getStringArray(R.array.thousands)

    fun formatCountToText(timerCount: Int): String = when {
        Locale.getDefault().language == "de" -> german(timerCount)
        Locale.getDefault().language == "ru" -> russian(timerCount)
        else -> default(timerCount)
    }

    private fun default(timerCount: Int): String {
        var suffix = ""

        if (timerCount / 10 % 10 > 1) {
            if (suffix.isNotEmpty()) suffix = "$suffix "
            suffix += tens[timerCount / 10 % 10 - 1]
        }

        if (timerCount / 10 % 10 == 1) {
            if (suffix.isNotEmpty()) suffix = "$suffix "
            suffix += if (timerCount % 10 == 0) tens[0] else teens[timerCount % 10 - 1]
        }

        if (timerCount % 10 != 0 && timerCount / 10 % 10 != 1) {
            if (suffix.isNotEmpty()) suffix = "$suffix-"
            suffix += units[timerCount % 10 - 1]
        }

        return "${buildPrefix(timerCount)} $suffix"
    }

    private fun german(timerCount: Int): String {
        var suffix = ""

        if (timerCount / 10 % 10 > 1) {
            suffix += tens[timerCount / 10 % 10 - 1]
        }

        if (timerCount / 10 % 10 == 1) {
            suffix += if (timerCount % 10 == 0) tens[0] else teens[timerCount % 10 - 1]
        }

        if (timerCount % 10 != 0 && timerCount / 10 % 10 != 1) {
            if (suffix.isNotEmpty()) suffix = "und$suffix"
            suffix = "${units[timerCount % 10 - 1]}$suffix"
        }

        return "${buildPrefix(timerCount)}$suffix"
    }

    private fun russian(timerCount: Int): String {
        var suffix = ""

        if (timerCount / 10 % 10 > 1) {
            if (suffix.isNotEmpty()) suffix = "$suffix "
            suffix += tens[timerCount / 10 % 10 - 1]
        }

        if (timerCount / 10 % 10 == 1) {
            if (suffix.isNotEmpty()) suffix = "$suffix "
            suffix += if (timerCount % 10 == 0) tens[0] else teens[timerCount % 10 - 1]
        }

        if (timerCount % 10 != 0 && timerCount / 10 % 10 != 1) {
            if (suffix.isNotEmpty()) suffix = "$suffix "
            suffix += units[timerCount % 10 - 1]
        }

        return "${buildPrefix(timerCount)} $suffix"
    }

    private fun buildPrefix(timerCount: Int): String {
        var prefix = ""

        if (timerCount > 1000) {
            return "∞"
        }

        if (timerCount / 1000 != 0) {
            prefix += thousands[timerCount / 1000 - 1]
        }

        if (timerCount / 100 != 0) {
            prefix += hundreds[timerCount / 100 - 1]
        }

        return prefix
    }
}