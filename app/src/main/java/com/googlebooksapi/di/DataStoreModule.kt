package com.googlebooksapi.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.googlebooksapi.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "datastore_preferences")

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext app: Context): DataStore<Preferences> {
        return app.dataStore
    }

    @Singleton
    @Provides
    fun providePreferencesKeyString(): Preferences.Key<String> {
        return stringPreferencesKey(Constants.CURRENT_QUERY)
    }

}