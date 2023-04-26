package com.mhmd.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mhmd.core.domain.ProgressBarState

@Composable
fun <T> List<T>.PaginatedLazyVerticalGrid(
    modifier: Modifier = Modifier,
    showEmptyView: Boolean = true,
    onLoadMore: () -> Unit,
    content: @Composable LazyGridItemScope.(T, Int) -> Unit,
    canLoadMore: Boolean,
    isLoading: ProgressBarState
) {


    val contentList = this
    Column {
        if (showEmptyView && contentList.isEmpty())
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
                item { EmptyView(modifier = modifier) }
            }
        else
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(contentList) { index, element ->
                    content(element, index)
                    if (index >= contentList.size - 1 && canLoadMore) {
                        LaunchedEffect(key1 = Unit, block = { onLoadMore.invoke() })
                    }
                }

                item(span = { GridItemSpan(2) }) {

                    if (isLoading is ProgressBarState.Loading && canLoadMore) {
                        LoadingItem(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                        Spacer(modifier = Modifier.height(32.dp))

                    }
                }
            }
    }

}
