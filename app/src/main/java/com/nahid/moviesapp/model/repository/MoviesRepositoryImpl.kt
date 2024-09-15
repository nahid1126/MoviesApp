package com.nahid.moviesapp.model.repository

import com.nahid.moviesapp.model.networks.ApiInterface
import javax.inject.Inject

private const val TAG = "MoviesRepositoryImpl"

class MoviesRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) {

}