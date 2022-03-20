package com.googlebooksapi.ui.presentation.paging3cache

import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.googlebooksapi.data.repository.Repository
import com.googlebooksapi.domain.model.Book
import com.googlebooksapi.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class Paging3CacheViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStore: DataStore<Preferences>,
    private val stringStoreKey: Preferences.Key<String>
) : ViewModel() {

    private val _booksCachedFlow = MutableStateFlow<PagingData<Book>>(PagingData.empty())
    val booksCachedFlow = _booksCachedFlow

    private var _query = mutableStateOf("")
    var query = _query

    init {
        viewModelScope.launch {
            dataStore.edit {
                _query.value = it[stringStoreKey] ?: Constants.DEFAULT_QUERY
            }
             repository.getBooksCachedFlow(query.value).cachedIn(viewModelScope).collect {
                 _booksCachedFlow.value = it
            }
        }
    }
}