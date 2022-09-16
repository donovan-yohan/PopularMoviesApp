package com.example.popularmoviesapp.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.popularmoviesapp.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PosterImage(poster_url: String?, width: Int = 102) {
    GlideImage(
        imageModel = poster_url,
        loading = {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        failure = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                Text(stringResource(R.string.network_image_error))

            }
        },
        modifier = Modifier
            .width(width.dp)
            .height((width * 1.5).dp)
    )
}