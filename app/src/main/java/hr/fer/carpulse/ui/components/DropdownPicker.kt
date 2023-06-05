package hr.fer.carpulse.ui.components

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import hr.fer.carpulse.R

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
            label = { Text(text = stringResource(id = R.string.fuel_type)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onBackground,
                trailingIconColor = Color.Gray,
                focusedTrailingIconColor = MaterialTheme.colors.onBackground,
                focusedIndicatorColor = MaterialTheme.colors.onBackground,
                unfocusedIndicatorColor = Color.Gray,
                focusedLabelColor = Color.Gray,
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