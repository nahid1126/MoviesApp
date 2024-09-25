package com.nahid.moviesapp.model.paging


import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.RecyclerView
import com.nahid.moviesapp.model.data.Movies
import com.nahid.moviesapp.model.local.MoviesDao

private const val TAG = "RetainCheckPagingSource"

class MoviesLocalPagingSource(
    private val moviesDao: MoviesDao,
    private val category: String
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
            val pageSize = params.loadSize
            val offset = (page - 1) * pageSize
            val response = moviesDao.getMoviesPagingByCategory(category, pageSize, offset)
            run {
                LoadResult.Page(
                    data = response,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (response.isEmpty()) null else page + 1
                )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}