package com.nahid.moviesapp.model.repository

import com.nahid.moviesapp.model.data.Movies
import com.nahid.moviesapp.model.local.LocalDatabase
import com.nahid.moviesapp.model.networks.ApiInterface
import com.nahid.moviesapp.model.networks.NetworkResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import java.nio.channels.UnresolvedAddressException
import javax.inject.Inject

private const val TAG = "MoviesRepositoryImpl"

class MoviesRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val localDatabase: LocalDatabase
) {
    private val moviesDao = localDatabase.moviesDao()
    var moviesListResponse = MutableSharedFlow<NetworkResponse<List<Movies>>>()
        private set

    suspend fun getMoviesList() {
        moviesListResponse.emit(NetworkResponse.Loading())
        try {
            val response = apiInterface.getMoviesList("popular", 1)
            when (response.code()) {
                in 200..299 -> {
                    moviesListResponse.emit(NetworkResponse.Success(response.body()!!))
                    moviesDao.insertMovies(response.body()!!)
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
}