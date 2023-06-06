package hr.fer.carpulse.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import hr.fer.carpulse.R

@Composable
fun ScreenTopBar(
    onBackArrowClick: () -> Unit = {},
    actions: @Composable () -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit = {},
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackArrowClick()
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = stringResource(
                                id = R.string.back
                            )
                        )
                    }
                },
                actions = {
                    actions()
                }
            )
        }
    ) { paddingValues ->

        content(paddingValues)
    }
}