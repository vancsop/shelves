package com.vancsop.shelves.presentation.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vancsop.shelves.presentation.design.PurpleGrey40

@Composable
fun GalleryListRow(
    modifier: Modifier,
    images: List<GalleryViewModel.Image>,
    onImagePress: (GalleryViewModel.Image) -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        LazyRow(
            modifier = Modifier
                .padding(bottom = 96.dp)
                .background(PurpleGrey40)
                .fillMaxWidth(),
            state = rememberLazyListState()
        ) {
            items(images) { item -> GalleryItem(item = item, onCLick = onImagePress) }
        }
    }
}

@Composable
fun GalleryItem(
    item: GalleryViewModel.Image,
    onCLick: (GalleryViewModel.Image) -> Unit
) {
    AsyncImage(
        modifier = Modifier
            .size(72.dp)
            .padding(8.dp)
            .clickable { onCLick(item) },
        model = item.path,
        contentDescription = item.label,
        contentScale = ContentScale.Crop
    )
}
