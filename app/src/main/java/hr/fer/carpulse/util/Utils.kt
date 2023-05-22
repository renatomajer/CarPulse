package hr.fer.carpulse.util

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import hr.fer.carpulse.BuildConfig


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