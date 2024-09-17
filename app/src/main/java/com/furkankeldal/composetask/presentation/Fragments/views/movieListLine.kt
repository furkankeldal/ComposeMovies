package com.furkankeldal.composetask.presentation.Fragments.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.furkankeldal.composetask.domain.model.Movie

@Composable
fun movieListLine(
    movieVertical : Movie,
    onItemClick : (Movie) -> Unit,

    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(movieVertical) }
            .padding(10.dp),

    ) {

        Column(modifier = Modifier.align(CenterVertically),horizontalAlignment = Alignment.CenterHorizontally) {


            Text(
                movieVertical.Title,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                color = White,
                textAlign = TextAlign.Center
            )

            Image(
                painter = rememberImagePainter(data = movieVertical.Poster),
                contentDescription = movieVertical.Title,
                modifier = Modifier
                    .padding(16.dp)
                    .size(200.dp, 200.dp)
                    .clip(RectangleShape)

            )
        }



    }
}