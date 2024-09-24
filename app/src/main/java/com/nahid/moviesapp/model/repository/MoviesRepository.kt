package com.nahid.moviesapp.model.repository

import com.nahid.moviesapp.model.data.Movies
import com.nahid.moviesapp.model.networks.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MoviesRepository {
    /* suspend fun requestForTweetsCategory()
     val tweetsCategoryResponse: SharedFlow<NetworkResponse<List<String>>>*/

    val movieListResponse: SharedFlow<NetworkResponse<List<Movies>>>
    suspend fun requestForMovies(category: String, page: Int)

    fun getMovieBtId(id: Int): Flow<Movies>

    suspend fun deleteMovies(category: String)
}