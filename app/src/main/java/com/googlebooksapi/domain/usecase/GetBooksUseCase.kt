package com.googlebooksapi.domain.usecase

import com.googlebooksapi.data.repository.Repository
import com.googlebooksapi.domain.model.Book
import com.googlebooksapi.utils.Constants
import com.googlebooksapi.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val booksRepository: Repository,
) {
    operator fun invoke(query: String, startIndex: String): Flow<Resource<List<Book>>> = flow {
        try {
            emit(Resource.Loading<List<Book>>())
            val books = booksRepository.getBooks(if(query.isNotEmpty()) query else Constants.DEFAULT_QUERY, startIndex)
            emit(Resource.Success<List<Book>>(books))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Book>>(e.localizedMessage ?: Constants.ERROR_MSG))
        } catch (e: IOException) {
            emit(Resource.Error<List<Book>>(e.localizedMessage ?: Constants.ERROR_MSG))
        }
    }
}