package com.nahid.moviesapp.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.RecyclerView
import com.nahid.moviesapp.model.data.Movies
import com.nahid.moviesapp.model.local.MoviesDao
import com.nahid.moviesapp.model.networks.ApiInterface
import com.nahid.moviesapp.utils.Constants
import java.io.IOException

class MoviesPagingSource(
    private val apiInterface: ApiInterface,
    private val category: String,
    private val moviesDao: MoviesDao
) : PagingSource<Int, Movies>() {
    override fun getRefreshKey(state: PagingState<Int, Movies>): Int? {
        val anchorPosition = state.anchorPosition
        return if (anchorPosition == null || anchorPosition == RecyclerView.NO_POSITION) {
            0
        } else {
            state.anchorPosition?.let {
                state.closestPageToPosition(it)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movies> {
        return try {
            val page = params.key ?: 1
            val response = apiInterface.getMoviesList(Constants.AUTH, category, page)
            if (!response.isSuccessful) {
                throw IOException("Failed :${response.message()} ${response.code()}")
            } else {
                val mainData = response.body()!!.results
                LoadResult.Page(
                    data = mainData,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (mainData.isEmpty()) null else page + 1
                )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}