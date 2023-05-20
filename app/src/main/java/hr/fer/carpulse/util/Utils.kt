package hr.fer.carpulse.util

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager

fun defaultKeyboardActions(focusManager: FocusManager) = KeyboardActions(
    onNext = {
        focusManager.moveFocus(FocusDirection.Down)
    },
    onDone = {
        focusManager.clearFocus()
    }
)