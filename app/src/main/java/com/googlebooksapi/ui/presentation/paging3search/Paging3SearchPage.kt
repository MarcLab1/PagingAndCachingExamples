package com.googlebooksapi.ui.presentation.paging3search

import com.googlebooksapi.ui.presentation.BookItem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.googlebooksapi.ui.presentation.common.BooksListEvent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BooksPagination3SearchPage(
    nav: NavController,
    booksViewModel: Paging3SearchViewModel = hiltViewModel()
) {
    val keyboard = LocalSoftwareKeyboardController.current
    var lazyBooks = booksViewModel.booksFlow.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 55.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var lazyListState = rememberLazyListState()

        Text(lazyBooks.itemCount.toString())

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    Row(modifier = Modifier.fillMaxWidth())
                    {
                        TextField(
                            value = booksViewModel.query.value,
                            onValueChange = {
                                booksViewModel.OnEvent(BooksListEvent.UpdateSearchQueryEvent(it))
                            },
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = ""
                                )
                            },
                            placeholder = {
                                Text(text = "Search books")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    booksViewModel.OnEvent(BooksListEvent.GetNewBooksEvent())
                                    keyboard?.hide()
                                })
                        )
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Button(onClick = {
                        booksViewModel.OnEvent(BooksListEvent.GetNewBooksEvent())
                        keyboard?.hide()
                    }) {
                        Text(text = "search")
                    }
                    Spacer(modifier = Modifier.padding(5.dp))

                }

                itemsIndexed(lazyBooks)
                { index, book ->
                    if (book != null) {

                        BookItem(index = index, book = book, OnClick = {
                            nav.navigate("volume/" + book.id)
                        })
                    }
                }
            }
            lazyBooks.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        MyCircleProgressBar()
                    }
                    loadState.append is LoadState.Loading -> {
                        MyCircleProgressBar()
                    }
                    loadState.refresh is LoadState.Error -> {
                        if (lazyBooks.itemCount == 0) {
                            val loadStateError = lazyBooks.loadState.refresh as LoadState.Error
                            MyErrorTextView(loadStateError.error.localizedMessage)
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        if (lazyBooks.itemCount == 0) {
                            val loadStateError = lazyBooks.loadState.append as LoadState.Error
                            MyErrorTextView(loadStateError.error.localizedMessage)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyCircleProgressBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp),
        horizontalArrangement = Arrangement.Center
    )
    {
        CircularProgressIndicator()
    }
}

@Composable
fun MyErrorTextView(error: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp),
        horizontalArrangement = Arrangement.Center
    )
    {
        Text(text = error, modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
    }
}