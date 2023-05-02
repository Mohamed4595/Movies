package com.moviedetails.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mhmd.components.Chip
import com.mhmd.components.GrayTransparentColor
import com.mhmd.components.R
import com.moviedetails.domain.ProductionCompanies

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductionCompaniesSection(
    modifier: Modifier = Modifier,
    productionCompanies: List<ProductionCompanies>,
) {

    Column {
        Text(
            modifier = modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.production_companies),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 48.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        ) {
            productionCompanies.forEach {
                if (it.name.isNotEmpty())
                    Chip(
                        modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
                        title = it.name,
                        isSelected = true,
                        selectedTextColor = MaterialTheme.colors.onBackground,
                        selectedBackGroundColor = GrayTransparentColor
                    )
            }
        }
    }
}