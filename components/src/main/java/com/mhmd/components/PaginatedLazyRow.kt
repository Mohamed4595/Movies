package com.mhmd.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> List<T>.PaginatedLazyRow(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onLoadMore: () -> Unit,
    content: @Composable LazyListScope.(T, Int) -> Unit,
    canLoadMore: Boolean
) {


    val contentList = this
    LazyRow(
        modifier = modifier,
        state = listState,
        contentPadding=contentPadding,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        itemsIndexed(contentList) { index, element ->
                this@LazyRow.content(element, index)
        }
        item {
            val isScrollToEnd by remember {
                derivedStateOf {
                    (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1)
                            && listState.isScrollInProgress
                }
            }
            if (isScrollToEnd && canLoadMore) {
                LoadingItem(
                    Modifier
                        .fillMaxHeight()
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.width(32.dp))
                onLoadMore.invoke()
            }
        }
    }

}
