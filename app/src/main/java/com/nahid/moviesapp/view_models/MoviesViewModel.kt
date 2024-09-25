package com.nahid.moviesapp.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nahid.moviesapp.di.qualifier.MoviesApiQualifier
import com.nahid.moviesapp.model.data.Movies
import com.nahid.moviesapp.model.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(@MoviesApiQualifier private val moviesRepository: MoviesRepository) :
    ViewModel() {

    val moviesList = moviesRepository.movieListResponse

    fun requestForMovies(category: String, page: Int) {
        viewModelScope.launch {
            moviesRepository.requestForMovies(category, page)
        }
    }

    fun getMovieById(id: Int) = moviesRepository.getMovieBtId(id)

    fun insetMovies(movies: List<Movies>) {
        viewModelScope.launch {
            moviesRepository.insertMovies(movies)
        }
    }

    fun insetMovie(movie: Movies) {
        viewModelScope.launch {
            moviesRepository.insertMovie(movie)
        }
    }

    fun getMoviesByCategory(category: String): Flow<PagingData<Movies>> =
        moviesRepository.getMovieBtCategory(category).cachedIn(viewModelScope)

    fun getMoviesByCategoryFromApi(category: String): Flow<PagingData<Movies>> =
        moviesRepository.getMovieByCategory(category).cachedIn(viewModelScope)
}