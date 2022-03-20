package com.googlebooksapi.ui.presentation

import android.os.Build
import android.text.Html
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.googlebooksapi.ui.presentation.bookdetail.BookDetailViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.googlebooksapi.R

@Composable
fun BookDetailPage(bookDetailViewModel: BookDetailViewModel = hiltViewModel()) {
    var bookDetailState = bookDetailViewModel.bookDetailState
    var book = bookDetailState.book
    Box()
    {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            if (book != null) {
                item{
                    GlideImage(
                        imageModel = book.thumbnail?.let { forceHttps(it) },
                        placeHolder = painterResource(R.drawable.book1),
                        error = painterResource(R.drawable.book1),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                            .padding(10.dp)
                    )

                    book.title?.let { Text(it, style = MaterialTheme.typography.h2) }
                    book.description?.let { Text(convertHtmlToString(it), style = MaterialTheme.typography.h4) }
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
        }
    }
    MyCircleProgressBar(showing = bookDetailState.isLoading)
}

private fun convertHtmlToString(input : String) : String{
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        return Html.fromHtml(input).toString()
    }
}
