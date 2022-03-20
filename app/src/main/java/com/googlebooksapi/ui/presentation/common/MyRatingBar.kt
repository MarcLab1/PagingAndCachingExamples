package com.googlebooksapi.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.googlebooksapi.R

@Composable
fun MyRatingBar(
    rating: Int,
    ratingsCount: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        when (rating) {
            0 -> {
                for (i in 1..5)
                    MyCustomStar(false)
            }
            1 -> {
                for (i in 1..4)
                    MyCustomStar(false)
                MyCustomStar(true)
            }
            2 -> {
                for (i in 1..3)
                    MyCustomStar(false)
                for (i in 1..2)
                    MyCustomStar(true)
            }
            3 -> {
                for (i in 1..2)
                    MyCustomStar(false)
                for (i in 1..3)
                    MyCustomStar(true)
            }
            4 -> {
                MyCustomStar(false)
                for (i in 1..4)
                    MyCustomStar(true)
            }
            5 -> {
                for (i in 1..5)
                    MyCustomStar(true)
            }
            else -> {
                for (i in 1..5)
                    MyCustomStar(false)
            }
        }
        Text(
            text = ratingsCount.toString(),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(start = 3.dp)
        )
    }
}


@Composable
fun MyCustomStar(filled: Boolean) {
    Image(
        if (filled) painterResource(id = R.drawable.star_filled) else painterResource(id = R.drawable.star_unfilled),
        contentDescription = "", modifier = Modifier
            .width(12.dp)
            .height(12.dp)
            .padding(1.dp)
    )
}
