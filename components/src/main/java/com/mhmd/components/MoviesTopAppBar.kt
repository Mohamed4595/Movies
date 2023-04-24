package com.mhmd.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopAppBarCardWrapper(
    topAppBarContent: @Composable () -> Unit
) {
    Card(
        elevation = 1.5.dp,
        backgroundColor = MaterialTheme.colors.background
    ) { topAppBarContent.invoke() }
}

@Composable
fun MoviesTopAppBar(
    modifier: Modifier=Modifier,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.h6.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onBackground,
        fontSize = 18.sp
    ),
    prefixIconRes: Int? = null,
    onPrefixIconClick: () -> Unit = {},
    isDivider: Boolean = true,
    suffixContent: @Composable () -> Unit = { Spacer(Modifier.fillMaxWidth()) }
) {
    Column(
        modifier = Modifier
            .fillMaxWidth().background(MaterialTheme.colors.surface)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {

            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                if (prefixIconRes != null) {
                    IconButton(prefixIconRes, iconColor = MaterialTheme.colors.onSurface, onClick = onPrefixIconClick)
                }
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = titleStyle
                )
            }
            suffixContent()
        }
        if (isDivider) AppBarDivider()
    }
}
