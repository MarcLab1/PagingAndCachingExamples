package com.googlebooksapi.ui.presentation.paging3cache

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.googlebooksapi.ui.presentation.BookItem

@OptIn(ExperimentalComposeUiApi::class, androidx.paging.ExperimentalPagingApi::class)
@Composable
fun Paging3CachePage(
    nav: NavController,
    paging3CacheViewModel: Paging3CacheViewModel = hiltViewModel()
) {

    var lazyBooksCached = paging3CacheViewModel.booksCachedFlow.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 55.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var lazyListState = rememberLazyListState()

        Text(lazyBooksCached.itemCount.toString())
        Text(paging3CacheViewModel.query.value)

        Box(modifier = Modifier.fillMaxSize())
        {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(lazyBooksCached)
                { index, book ->
                    if (book != null) {

                        BookItem(index = index, book = book, OnClick = {
                            nav.navigate("volume/" + book.id)
                        })
                    }
                }
            }
        }
    }
}
