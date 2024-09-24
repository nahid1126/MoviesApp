package com.nahid.moviesapp.view_models

import com.nahid.moviesapp.di.qualifier.MoviesApiQualifier
import com.nahid.moviesapp.model.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(@MoviesApiQualifier private val moviesRepository: MoviesRepository) {
    val moviesList = moviesRepository.movieListResponse

    suspend fun requestForMovies(category: String, page: Int) {
        moviesRepository.requestForMovies(category, page)
    }

    fun getMovieById(id: Int) = moviesRepository.getMovieBtId(id)

}