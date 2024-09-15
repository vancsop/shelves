package com.vancsop.shelves.presentation.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vancsop.shelves.presentation.design.PurpleGrey40

@Composable
fun GallerySingleImage(
    modifier: Modifier,
    image: GalleryViewModel.Image
) {
    AsyncImage(
        modifier = modifier,
        model = image.path,
        contentDescription = image.label,
        contentScale = ContentScale.Crop
    )
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            modifier = Modifier.background(PurpleGrey40).padding(8.dp).fillMaxWidth(),
            text = image.label
        )
    }
}
