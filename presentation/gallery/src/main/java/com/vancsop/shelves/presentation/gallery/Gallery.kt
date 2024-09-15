package com.vancsop.shelves.presentation.gallery

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.vancsop.shelves.presentation.design.ShelvesTheme
import kotlinx.coroutines.launch

@Composable
fun Gallery(
    state: State<GalleryViewModel.State>,
    onImagePress: (GalleryViewModel.Image) -> Unit,
    onExportPress: () -> Unit
) {
    ShelvesTheme {
        val scope = rememberCoroutineScope()
        val snackState = remember { SnackbarHostState() }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                val snackMessage = stringResource(id = R.string.gallery_export_message)
                ExtendedFloatingActionButton(
                    onClick = {
                        scope.launch { snackState.showSnackbar(snackMessage) }
                        onExportPress()
                    },
                    icon = { Icon(Icons.Filled.Share, stringResource(id = R.string.gallery_export)) },
                    text = { Text(text = stringResource(id = R.string.gallery_export)) },
                )
            },

            snackbarHost = {
                SnackbarHost(hostState = snackState)
            }
        ) { innerPadding ->
            when (val currentState = state.value) {
                is GalleryViewModel.State.Loading -> GalleryLoading(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )

                is GalleryViewModel.State.Empty -> GalleryEmpty(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )

                is GalleryViewModel.State.Content -> {
                    GallerySingleImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        image = currentState.selected
                    )
                    GalleryListRow(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        images = currentState.images,
                        onImagePress = onImagePress
                    )
                }
            }
        }
    }
}
