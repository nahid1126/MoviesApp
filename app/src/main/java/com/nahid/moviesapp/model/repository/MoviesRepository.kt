package com.nahid.moviesapp.model.repository

import com.nahid.moviesapp.model.networks.NetworkResponse
import kotlinx.coroutines.flow.SharedFlow

interface MoviesRepository {
    suspend fun requestForTweetsCategory()
    val tweetsCategoryResponse: SharedFlow<NetworkResponse<List<String>>>

    suspend fun requestForTweets(category: String)
    val tweetsResponse: SharedFlow<NetworkResponse<List<TweetList>>>
}