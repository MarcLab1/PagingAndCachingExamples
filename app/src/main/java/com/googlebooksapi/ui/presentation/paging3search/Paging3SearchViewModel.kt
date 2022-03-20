package com.googlebooksapi.ui.presentation.paging3search

import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.googlebooksapi.data.repository.Repository
import com.googlebooksapi.domain.model.Book
import com.googlebooksapi.ui.presentation.common.BooksListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Paging3SearchViewModel
@Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val stringStoreKey: Preferences.Key<String>,
    private val repository: Repository
) : ViewModel() {

    private val _query = mutableStateOf("")
    val query = _query

    private val _booksFlow = MutableStateFlow<PagingData<Book>>(PagingData.empty())
    val booksFlow = _booksFlow

    fun OnEvent(event: BooksListEvent) {
        when (event) {
            is BooksListEvent.UpdateSearchQueryEvent -> { updateQuery(event.query) }
            is BooksListEvent.GetNewBooksEvent -> { searchBooksFlow() }
        }
    }

    fun searchBooksFlow() {
        viewModelScope.launch {
            repository.searchBooksFlow(query.value).cachedIn(viewModelScope).collect {
                _booksFlow.value = it
            }
        }
    }

    private fun updateQuery(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            dataStore.edit {
                it[stringStoreKey] = newQuery
            }
        }
    }

}