package com.agaperra.weatherforecast.presentation.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.agaperra.weatherforecast.presentation.theme.secondOrangeDawn
import com.agaperra.weatherforecast.utils.Constants
import com.agaperra.weatherforecast.utils.Constants.TOP_APPBAR_HEIGHT

@Composable
fun SearchAppBar(
    searchedTextState: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APPBAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = secondOrangeDawn
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchedTextState,
            onValueChange = { text -> onTextChange(text) },
            placeholder = {
                Text(
                    text = "Search",
                    color = Color.White,
                    modifier = Modifier.alpha(ContentAlpha.medium)
                )
            },
            textStyle = TextStyle(
                color = Color.White,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = { onSearchClicked(searchedTextState) },
                    modifier = Modifier.alpha(ContentAlpha.disabled)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { onTextChange("") }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardActions = KeyboardActions(onSearch = { onSearchClicked(searchedTextState) }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}