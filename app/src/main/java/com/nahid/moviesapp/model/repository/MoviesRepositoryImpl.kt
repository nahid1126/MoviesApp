package com.nahid.moviesapp.model.repository

import com.nahid.moviesapp.model.data.Movies
import com.nahid.moviesapp.model.local.LocalDatabase
import com.nahid.moviesapp.model.networks.ApiInterface
import com.nahid.moviesapp.model.networks.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.nio.channels.UnresolvedAddressException
import javax.inject.Inject

private const val TAG = "MoviesRepositoryImpl"

class MoviesRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val localDatabase: LocalDatabase
) : MoviesRepository {
    private val moviesDao = localDatabase.moviesDao()
    private val moviesListResponse = MutableSharedFlow<NetworkResponse<List<Movies>>>()

    override val movieListResponse: SharedFlow<NetworkResponse<List<Movies>>>
        get() = moviesListResponse.asSharedFlow()

    override suspend fun requestForMovies(category: String, page: Int) {
        moviesListResponse.emit(NetworkResponse.Loading())
        try {
            val response = apiInterface.getMoviesList(category, page)
            when (response.code()) {
                in 200..299 -> {
                    moviesListResponse.emit(NetworkResponse.Success(response.body()!!))
                    insertMovies(response.body()!!)
                }

                in 400..499 -> {
                    moviesListResponse.emit(NetworkResponse.Error(response.message()))
                }

                in 500..599 -> {
                    moviesListResponse.emit(NetworkResponse.Error(response.message()))
                }
            }
        } catch (e: UnresolvedAddressException) {
            moviesListResponse.emit(NetworkResponse.Error("No Internet Connection"))
        } catch (e: Exception) {
            moviesListResponse.emit(NetworkResponse.Error("Exception: Unable to Communicate with Server"))
        }
    }

    override fun getMovieBtId(id: Int): Flow<Movies> {
        return moviesDao.getMoviesById(id)
    }

    private suspend fun insertMovies(movies: List<Movies>) {
        moviesDao.insertMovies(movies)
    }

    override suspend fun deleteMovies(category: String) {
        moviesDao.deleteMovies(category)
    }
}