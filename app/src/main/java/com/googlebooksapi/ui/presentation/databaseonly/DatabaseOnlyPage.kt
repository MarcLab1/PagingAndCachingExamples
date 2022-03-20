package com.googlebooksapi.ui.presentation.databaseonly

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.googlebooksapi.ui.presentation.BookItem

@Composable
fun DatabaseOnlyPage(nav: NavController) {
    var databaseOnlyViewModel: DatabaseOnlyViewModel = hiltViewModel()
    var books = databaseOnlyViewModel.booksFlow.collectAsState(initial = listOf())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 55.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        LazyColumn()
        {
            itemsIndexed(books.value)
            { index, book ->
                BookItem(index = index, book = book, OnClick = {
                    nav.navigate("volume/" + book.id)
                })
            }
        }
    }
}