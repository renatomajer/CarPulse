package hr.fer.carpulse.util

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import hr.fer.carpulse.BuildConfig
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun defaultKeyboardActions(focusManager: FocusManager) = KeyboardActions(
    onNext = {
        focusManager.moveFocus(FocusDirection.Down)
    },
    onDone = {
        focusManager.clearFocus()
    }
)

fun getAppVersion(): String {
    return BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")"
}

fun getDateTime(timestamp: Long): String? {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val netDate = Date(timestamp)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}

fun getDate(timestamp: Long): String {
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val date = Date(timestamp)
    return simpleDateFormat.format(date)
}

fun getTime(timestamp: Long): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val date = Date(timestamp)
    return simpleDateFormat.format(date)
}

fun getHoursMinutesSeconds(timestamp: Long): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val date = Date(timestamp)
    return simpleDateFormat.format(date)
}

fun calculateDurationInMinutes(startTimestamp: Long, endTimestamp: Long): Int {
    val diff = endTimestamp - startTimestamp
    return diff.div(60000).toInt()
}

fun getHoursMinutesFromMinutesDuration(minutesDuration: Int): String {
    if (minutesDuration < 60) return "$minutesDuration min"

    val hours: Int = minutesDuration / 60
    val remainingMinutes: Int = minutesDuration % 60
    return "${hours}h ${remainingMinutes}min"
}

fun getMinutesOrSecondsFromMinutesDuration(minutesDuration: Double): String {
    if (minutesDuration < 1) return (minutesDuration * 60.0).toInt().toString() + " sec"

    return minutesDuration.toInt().toString() + " min"
}