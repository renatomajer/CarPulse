package hr.fer.carpulse.util

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import hr.fer.carpulse.BuildConfig
import java.text.SimpleDateFormat
import java.util.*


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