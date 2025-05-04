package hr.fer.carpulse.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import hr.fer.carpulse.ui.theme.Purple200
import hr.fer.carpulse.ui.theme.Typography
import hr.fer.carpulse.ui.theme.thinText

@Composable
fun CarPulseTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onChange: (String) -> Unit = {},
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions(),
    isEnabled: Boolean = true,
    readOnly: Boolean = false
) {

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onChange,
        leadingIcon = leadingIcon,
        textStyle = thinText,
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        keyboardActions = keyboardActions,
        enabled = isEnabled,
        readOnly = readOnly,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = if (readOnly) Purple200 else MaterialTheme.colors.onBackground,
            focusedBorderColor = MaterialTheme.colors.onBackground,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            disabledTextColor = Color.Black,
            backgroundColor = Color.White
        ),
        placeholder = {
            Text(text = placeholder, style = Typography.body1, color = Color.LightGray)
        },
        shape = RoundedCornerShape(13)
    )

}