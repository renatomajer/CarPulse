package hr.fer.carpulse.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import hr.fer.carpulse.ui.theme.microPadding

@Composable
fun DataTextField(
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
        modifier = modifier.fillMaxWidth().padding(top = microPadding, bottom = microPadding),
        value = value,
        onValueChange = onChange,
        leadingIcon = leadingIcon,
        textStyle = Typography.body1,
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        keyboardActions = keyboardActions,
        enabled = isEnabled,
        readOnly = readOnly,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = if (readOnly) Purple200 else MaterialTheme.colors.onBackground,
            focusedBorderColor = MaterialTheme.colors.onBackground,
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = Color.Gray,
            disabledTextColor = Color.Black,
        ),
        placeholder = {
            Text(text = placeholder, style = Typography.body1, color = Color.LightGray)
        },
        label = {
            Text(text = placeholder, style = Typography.body2, color = Color.Gray)
        }
    )

}