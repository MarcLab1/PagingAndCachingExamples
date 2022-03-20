package com.googlebooksapi.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.googlebooksapi.data.ApiService
import com.googlebooksapi.data.repository.Repository
import com.googlebooksapi.data.repository.Repository_Impl
import com.googlebooksapi.domain.database.BooksDao
import com.googlebooksapi.domain.database.Database
import com.googlebooksapi.domain.database.Converters
import com.googlebooksapi.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesApiService(): ApiService {
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesBooksRepository(apiService: ApiService, booksDatabase: Database): Repository {
        return Repository_Impl(apiService, booksDatabase)
    }

    @Singleton
    @Provides
    fun providesBooksDatabase(app: Application): Database {
        return Room.databaseBuilder(app, Database::class.java, "books_db")
            .addTypeConverter(Converters()).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesDao(booksDatabase: Database): BooksDao {
        return booksDatabase.booksDao()
    }

}