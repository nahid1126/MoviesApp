package com.nahid.moviesapp.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nahid.moviesapp.model.data.Movies

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movies>)

    @Query("SELECT * FROM Movies WHERE category = :category")
    suspend fun getMovies(category: String): List<Movies>

    @Query("DELETE FROM Movies WHERE category = :category")
    suspend fun deleteMovies(category: String)
}