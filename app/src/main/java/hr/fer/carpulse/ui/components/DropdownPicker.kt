package hr.fer.carpulse.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import hr.fer.carpulse.ui.theme.thinText

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownPicker(
    modifier: Modifier = Modifier,
    values: Array<String>,
    selectedItem: String,
    onOptionSelected: (String) -> Unit
) {

    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }

    // box
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        // text field
        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onBackground,
                trailingIconColor = Color.Black,
                focusedTrailingIconColor = MaterialTheme.colors.onBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Transparent,
                placeholderColor = Color.LightGray,
                backgroundColor = Color.White
            ),
            shape = RoundedCornerShape(13),
            textStyle = thinText
        )

        // menu
        ExposedDropdownMenu(
            modifier = modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // this is a column scope
            // all the items are added vertically
            values.forEach { selectedOption ->
                // menu item
                DropdownMenuItem(onClick = {
                    onOptionSelected(selectedOption)
                    expanded = false
                }) {
                    Text(text = selectedOption)
                }
            }
        }
    }
}