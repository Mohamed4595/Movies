package com.mhmd.components.utils


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.mhmd.components.R
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale


const val DATE_TIME_FORMAT = "yyyy/MM/dd hh:mm aa"
const val DATE_FORMAT = "yyyy/MM/dd"
const val TIME_FORMAT = "hh:mm aa"

data class DurationValues(
    val days: Int,
    val hours: Int,
    val minutes: Int
)


fun Context.minutesToHoursMinutes(timeInMinutes: Long): String {
    return timeInMinutes.run {
        val hours = this / 60
        val minutes = this % 60

        val result: StringBuilder = java.lang.StringBuilder()

        if (hours > 0) {
            result.append(getString(R.string.hour_char, hours))
            if (minutes > 0) {
                result.append(" : ")
            }
        }
        if (minutes > 0) result.append(getString(R.string.minute_char, minutes))
        result.toString()
    }
}

fun Context.secondsToHoursMinutesSeconds(timeInSeconds: Long): String {
    return timeInSeconds.run {
        val hours = this / 60 / 60
        val minutes = this / 60 % 60
        val seconds = this % 60

        val result: StringBuilder = java.lang.StringBuilder()

        result.append(getString(R.string.hour_char, hours))
        result.append(" : ")
        result.append(getString(R.string.minute_char, minutes))
        result.append(" : ")
        result.append(getString(R.string.second_char, seconds))

        result.toString()
    }
}

fun Context.minutesToDaysHoursMinutes(timeInMinutes: Long): String {
    return timeInMinutes.run {
        val days = this / 24 / 60
        val hours = this / 60 % 24
        val minutes = this % 60

        val result: StringBuilder = java.lang.StringBuilder()

        if (days > 0) {
            result.append(getString(R.string.day_char, days))
            if (hours > 0 || minutes > 0) {
                result.append(" , ")
            }
        }

        if (hours > 0) {
            result.append(getString(R.string.hour_char, hours))
            if (minutes > 0) {
                result.append(" : ")
            }
        }
        if (minutes > 0) result.append(getString(R.string.minute_char, minutes))
        result.toString()
    }
}

fun Context.convertTimeStampToDateTime(timeStamp: Date): String {
    val now = Calendar.getInstance()

    val date = Calendar.getInstance()
    date.timeInMillis = timeStamp.time

    val locale = Locale.US

    return if (now.get(Calendar.DATE) == date.get(Calendar.DATE)) {
        SimpleDateFormat("hh:mm aa", locale).format(timeStamp)
    } else {
        SimpleDateFormat("dd-MM-yyy hh:mm aa", locale).format(timeStamp)
    }
}

fun Context.getPrettyDate(timeStamp: Date, addTimeToYesterday: Boolean): String {
    val now = Calendar.getInstance()

    val date = Calendar.getInstance()
    date.timeInMillis = timeStamp.time

    val locale = Locale.US

    return if (now.get(Calendar.DATE) == date.get(Calendar.DATE)) {
        SimpleDateFormat("h:mm a", locale).format(timeStamp)
    } else if (now.get(Calendar.DATE) - date.get(Calendar.DATE) == 1) {
        if (addTimeToYesterday) {
            getString(R.string.yesterday_at) + SimpleDateFormat("h:mm a", locale).format(
                timeStamp
            )
        } else {
            getString(R.string.yesterday)
        }
    } else {
        SimpleDateFormat("d/M/yy", locale).format(timeStamp)
    }
}

fun getTranslatedDateTime(localDateTime: LocalDateTime): String {
    val local = Locale.US

    val outputFormat: java.text.DateFormat = SimpleDateFormat(DATE_TIME_FORMAT, local)

    val inputFormat: java.text.DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.US)

    return try {
        val date = inputFormat.parse(localDateTime.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

fun getTranslatedDate(localDate: LocalDate): String {
    val local = Locale.US
    val outputFormat: java.text.DateFormat = SimpleDateFormat(DATE_FORMAT, local)
    val inputFormat: java.text.DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    return try {
        val date = inputFormat.parse(localDate.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

fun getTranslatedDateWithMonthName(localDate: LocalDate): String {
    val local = Locale.US
    val outputFormat: java.text.DateFormat = SimpleDateFormat("dd MMM yyyy", local)
    val inputFormat: java.text.DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    return try {
        val date = inputFormat.parse(localDate.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

fun getTranslatedDateWithMonthNameAndDayName(
    localDate: LocalDate
): String {
    val local = Locale.US
    val outputFormat: java.text.DateFormat = SimpleDateFormat("EEEE, dd MMM yyyy", local)
    val inputFormat: java.text.DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    return try {
        val date = inputFormat.parse(localDate.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

fun getTranslatedDateWithMonthNameWithoutYear(
    localDate: LocalDate
): String {
    val local = Locale.US
    val outputFormat: java.text.DateFormat = SimpleDateFormat("dd MMM", local)
    val inputFormat: java.text.DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    return try {
        val date = inputFormat.parse(localDate.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

fun getTranslatedDateWithDay(localDate: LocalDate): String {
    val local = Locale.US
    val outputFormat: java.text.DateFormat = SimpleDateFormat("dd", local)
    val inputFormat: java.text.DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    return try {
        val date = inputFormat.parse(localDate.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

fun getTranslatedDateWithPattern(
    pattern: String,
    localDate: LocalDate
): String {
    val local = Locale.US
    val outputFormat: java.text.DateFormat = SimpleDateFormat(pattern, local)
    val inputFormat: java.text.DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    return try {
        val date = inputFormat.parse(localDate.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

fun getTranslatedTime(selectedLanguage: String, localTime: LocalTime): String {
    val local = Locale.US
    val outputFormat: java.text.DateFormat = SimpleDateFormat(TIME_FORMAT, local)
    val inputFormat: java.text.DateFormat = SimpleDateFormat("HH:mm", Locale.US)

    return try {
        val date = inputFormat.parse(localTime.toString())
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.convertMillisToZonedDate(): LocalDate {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.convertZonedToDateMillis(): Long {
    return this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}