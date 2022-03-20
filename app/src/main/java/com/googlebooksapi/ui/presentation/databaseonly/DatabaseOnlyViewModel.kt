package com.googlebooksapi.ui.presentation.databaseonly

import androidx.lifecycle.ViewModel
import com.googlebooksapi.data.repository.Repository
import com.googlebooksapi.domain.database.Database
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DatabaseOnlyViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var booksFlow = repository.getBooksFromDbFlow()
}