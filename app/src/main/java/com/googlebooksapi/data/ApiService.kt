package com.googlebooksapi.data

import com.googlebooksapi.data.dto.BookResponseDto
import com.googlebooksapi.data.dto.BooksResponseDto
import com.googlebooksapi.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("volumes")
    suspend fun getBooksByStartIndex(
        @Query("q") query: String,
        @Query("startIndex") startIndex : String = "0",
        @Query("key") key: String = Constants.API_KEY,
    ) : BooksResponseDto

    @GET("volumes/{volumeId}")
    suspend fun getBookByVolumeId(
        @Path("volumeId") volumeId: String,
        @Query("key") key: String = Constants.API_KEY,
    ) : BookResponseDto

}