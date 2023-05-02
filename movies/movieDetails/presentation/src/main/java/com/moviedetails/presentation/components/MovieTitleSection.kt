package com.moviedetails.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mhmd.core.util.getTranslatedDateWithMonthNameAndDayName
import java.time.LocalDate

@Composable
fun MovieTitleSection(title:String?,date: LocalDate?) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Column(
        modifier = Modifier
            .padding(start = (screenWidth / 3) + 24.dp)
            .fillMaxWidth()
            .height(screenHeight / 9)
    ) {
        Text(
            text = title ?: "",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        date?.let {
            Text(
                text = getTranslatedDateWithMonthNameAndDayName(it),
                style = MaterialTheme.typography.overline
            )
        }
    }
}