package com.vancsop.shelves.presentation.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vancsop.shelves.domain.catalogue.ExportItemsUseCase
import com.vancsop.shelves.domain.catalogue.GetItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    val getItems: GetItemsUseCase,
    val exportItems: ExportItemsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch {
            getItems().collectLatest(::onUpdate)
        }
    }

    fun onImagePress(image: Image) {
        val currentState = _state.value
        if (currentState is State.Content) {
            viewModelScope.launch {
                _state.emit(currentState.copy(selected = image))
            }
        }
    }

    fun onExportPress() {
        val currentState = _state.value
        if (currentState !is State.Content) return

        viewModelScope.launch { exportItems() }
    }

    private suspend fun onUpdate(items: List<GetItemsUseCase.Item>) {
        if (items.isEmpty()) {
            _state.emit(State.Empty)
            return
        }

        val images = items.map {
            Image(label = "${it.shelfId}: ${it.classification}", path = it.path)
        }

        _state.emit(
            State.Content(
                selected = images.first(),
                images = images
            )
        )
    }

    sealed interface State {
        data object Loading : State
        data object Empty : State
        data class Content(
            val selected: Image,
            val images: List<Image>
        ) : State
    }

    data class Image(val label: String, val path: String)
}
