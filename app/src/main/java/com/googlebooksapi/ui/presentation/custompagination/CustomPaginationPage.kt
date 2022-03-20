package com.googlebooksapi.ui.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.googlebooksapi.ui.presentation.common.BooksListEvent
import com.googlebooksapi.ui.presentation.custompagination.CustomPaginationViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomPagination(nav: NavController, booksViewModel: CustomPaginationViewModel = hiltViewModel()) {
    var booksState = booksViewModel.booksState
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 55.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var lazyListState = rememberLazyListState()

        Box(modifier = Modifier.fillMaxSize())
        {
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
                            value = booksState.query,
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

                itemsIndexed(booksState.books)
                { index, book ->
                    booksViewModel.updateCurrentScrollIndex(index)
                    if (book != null) {

                        BookItem(index = index, book = book, OnClick = {
                            nav.navigate("volume/" + book.id)
                        })

                        if (((index + 1) >= booksState.books.size) && !booksState.isLoading && !booksState.isLastPage)
                            booksViewModel.OnEvent(BooksListEvent.GetMoreBooksEvent())
                    }
                }
            }

            if (booksState.isLoading)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            if (booksState.books.size == 0 && booksState.error.isNotEmpty()) {
                Text(
                    booksState.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 10.dp, end = 10.dp)
                )
            }
        }
    }
}

