package com.nahid.moviesapp.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nahid.moviesapp.model.data.Movies

@Database(entities = [Movies::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}