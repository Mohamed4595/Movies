package com.moviedetails.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mhmd.components.Chip
import com.mhmd.components.DarkColorSurface
import com.mhmd.components.GrayTransparentColor
import com.mhmd.components.R
import com.moviedetails.domain.ProductionCompanies

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductionCompaniesSection(
    modifier: Modifier = Modifier,
    productionCompanies: List<ProductionCompanies>,
    imageLoader: ImageLoader
) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

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
                if(it.name.isNotEmpty())
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