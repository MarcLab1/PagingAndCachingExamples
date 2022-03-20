package com.googlebooksapi.ui.presentation

import android.widget.ProgressBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyCircleProgressBar(showing: Boolean) {
    if (showing) {
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
}