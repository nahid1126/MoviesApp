package com.nahid.moviesapp.model.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nahid.moviesapp.model.data.Movies
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movies>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: Movies)

    @Query("SELECT * FROM Movies WHERE id = :id")
    fun getMoviesById(id: Int): Flow<Movies>

    @Query("SELECT * FROM Movies WHERE category = :category")
    fun getMoviesPagingByCategory(category: String): Flow<List<Movies>>

    @Query("SELECT * FROM movies WHERE category = :category LIMIT :limit OFFSET :offset")
    fun getMoviesPagingByCategory(category: String, limit: Int, offset: Int): List<Movies>


    @Query("SELECT * FROM Movies WHERE category = :category")
    fun getMoviesByCategory(category: String): PagingSource<Int, Movies>

    @Query("DELETE FROM Movies WHERE category = :category")
    suspend fun deleteMovies(category: String)
}