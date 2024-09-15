package com.nahid.moviesapp.model.networks

import com.nahid.moviesapp.utils.Constants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("b/66b9d97be41b4d34e41f33b7?meta=false")
    suspend fun getTweets(
        @Header("X-JSON-path") category: String
    ): Response<List<TweetList>>

    @GET("b/66b9d97be41b4d34e41f33b7?meta=false")
    @Headers("X-JSON-Path:tweets..category")
    suspend fun getTweetsCategory(): Response<List<String>>


    @GET("{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<ResponseBody>
}