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

class GetBookUseCase @Inject constructor(
    private val booksRepository: Repository,
) {
    operator fun invoke(volumeId: String): Flow<Resource<Book>> = flow {
        try {
            emit(Resource.Loading<Book>())
            val book = booksRepository.getBookByVolumeId(volumeId)
            emit(Resource.Success<Book>(book))
        } catch (e: HttpException) {
            emit(Resource.Error<Book>(e.localizedMessage ?: Constants.ERROR_MSG))
        } catch (e: IOException) {
            emit(Resource.Error<Book>(e.localizedMessage ?: Constants.ERROR_MSG))
        }
    }
}