package com.vancsop.shelves.presentation.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vancsop.shelves.domain.catalogue.AddItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    val addItem: AddItemUseCase
): ViewModel() {
    private val _state = MutableStateFlow<State>(State.Initial)
    val state: StateFlow<State> = _state

    fun onCameraResult(classification: String) {
        val shelfId = _state.value.shelfId
        if (shelfId.isEmpty() || classification.isEmpty()) return

        viewModelScope.launch {
            _state.emit(State.Classified(shelfId = shelfId, classification = classification))
        }
    }

    fun onAddPress(bitmap: Bitmap) {
        viewModelScope.launch {
            when (val currentState = state.value) {
                is State.Classifying,
                is State.Initial -> return@launch
                is State.Classified -> {
                    addItem(
                        classification = currentState.classification,
                        shelfId = currentState.shelfId,
                        bitmap
                    )
                    _state.emit(State.Classifying(currentState.shelfId))
                }
            }
        }
    }

    fun onShelfIdChange(shelfId: String) {
        viewModelScope.launch {
            _state.emit(State.Classifying(shelfId))
        }
    }

    sealed interface State {
        val shelfId: String

        data object Initial : State {
            override val shelfId: String = ""
        }
        data class Classifying(
            override val shelfId: String
        ): State
        data class Classified(
            override val shelfId: String,
            val classification: String
        ): State
    }
}
