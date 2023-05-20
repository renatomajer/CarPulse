package hr.fer.carpulse.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import hr.fer.carpulse.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownPicker(
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
        modifier = Modifier.fillMaxWidth(),
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
            label = { Text(text = stringResource(id = R.string.fuel_type)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                trailingIconColor = Color.Gray,
                focusedTrailingIconColor = Color.Black,
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.LightGray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Gray,
                placeholderColor = Color.LightGray
            )
        )

        // menu
        ExposedDropdownMenu(
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