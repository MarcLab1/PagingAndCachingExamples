package com.googlebooksapi.ui.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.googlebooksapi.domain.model.Book
import com.skydoves.landscapist.glide.GlideImage
import com.googlebooksapi.R

@Composable
fun BookItem(index: Int, book: Book, OnClick: () -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .clickable {
                OnClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp), verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(index.toString())

            Column(
                modifier = Modifier
                    .fillMaxWidth(.25f)
                    .padding(end = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                GlideImage(
                    imageModel = forceHttps(book.thumbnail),
                    placeHolder = painterResource(R.drawable.book1),
                    error = painterResource(R.drawable.book1),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(75.dp)
                        .height(75.dp)
                )
            }
            Column() {

                Text(
                    book.title,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h2
                )

                Text(
                    book.publisher,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h3
                )

                Row()
                {
                    var authorsString = "by "
                    for (author in book.authors) {
                        authorsString += author + ", "
                    }
                    Text(authorsString.dropLast(2), style = MaterialTheme.typography.h4)
                }

                if(book.ratingsCount !=0) {
                    MyRatingBar(
                        rating = book.averageRating.toInt(),
                        ratingsCount = book.ratingsCount
                    )
                }
            }
        }
    }
}


fun forceHttps(input: String): String {
    if (input == null || input.length == 0) return ""

    return "https" + input.subSequence(input.indexOf(':'), input.length)
}
