package com.googlebooksapi.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.googlebooksapi.domain.model.Book
import com.googlebooksapi.domain.model.RemoteKeys

@Database (entities = [Book::class, RemoteKeys::class], version = 1)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase(){

    abstract fun booksDao() : BooksDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}