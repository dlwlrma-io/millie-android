package io.dlwlrma.millie.ui.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dlwlrma.millie.domain.model.News
import io.dlwlrma.millie.domain.repository.NewsRepository
import io.dlwlrma.millie.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(UiState.Success(emptyList()))
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            newsRepository.getNews()
                .collect { favoriteNews ->
                    _uiState.value = UiState.Success(favoriteNews)
                }
        }
    }

    sealed class UiState {
        data class Success(val news: List<News>) : UiState()
        data class Error(val exception: Throwable) : UiState()
    }
}