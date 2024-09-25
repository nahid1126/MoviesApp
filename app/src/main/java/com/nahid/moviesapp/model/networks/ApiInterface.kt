package com.nahid.moviesapp.model.networks

import com.nahid.moviesapp.model.data.MoviesList
import com.nahid.moviesapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("{category}")
    suspend fun getMoviesList(
        @Header("Authorization") token: String,
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<MoviesList>
}