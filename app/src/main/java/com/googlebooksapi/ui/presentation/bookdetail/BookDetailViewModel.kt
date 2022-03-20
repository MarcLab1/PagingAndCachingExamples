package com.googlebooksapi.ui.presentation.bookdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.googlebooksapi.domain.usecase.GetBookUseCase
import com.googlebooksapi.utils.Constants
import com.googlebooksapi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookUseCase: GetBookUseCase,
    private val savedStateHandle : SavedStateHandle
) : ViewModel() {

    var bookDetailState by mutableStateOf(BookDetailState())
        private set

    init {
        savedStateHandle.get<String>(Constants.VOLUME_ID)?.let { getBook(it) }
    }

    fun getBook(volumeId: String) {
        bookUseCase(volumeId = volumeId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    this.bookDetailState = this.bookDetailState.copy(
                        book = result.data,
                        isLoading = false, error = "",
                    )
                }
                is Resource.Error -> {
                    this.bookDetailState = this.bookDetailState.copy(
                        isLoading = false,
                        error = result.message ?: Constants.ERROR_MSG
                    )
                }
                is Resource.Loading -> {
                    this.bookDetailState = this.bookDetailState.copy(isLoading = true, error = "")
                }
            }
        }.launchIn(viewModelScope)
    }

}