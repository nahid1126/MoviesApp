package com.nahid.moviesapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.nahid.moviesapp.model.data.Movies
import com.nahid.moviesapp.ui.theme.MoviesAppTheme
import com.nahid.moviesapp.utils.isNetworkAvailable
import com.nahid.moviesapp.view_models.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "popular",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val viewModel: MoviesViewModel = hiltViewModel<MoviesViewModel>()
    val context = LocalContext.current
    val moviesResponse = viewModel.getMoviesByCategoryFromApi(name).collectAsLazyPagingItems()
    LazyColumn {
        items(moviesResponse.itemCount) { index ->
            moviesResponse[index]?.let {
                it.category = name
                MovieItem(movie = it)
                viewModel.insetMovie(it)
            }
        }
        moviesResponse.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                loadState.append is LoadState.NotLoading && loadState.append.endOfPaginationReached -> {
                    item {
                        Text("No more movies available.")
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    item { Text("Error: ${error.error.message}") }
                }
            }
        }
    }

}



@Composable
fun MovieItem(movie: Movies) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp, 10.dp),
        border = BorderStroke(1.dp, Color(0xFFCCCCCC)),
        content = {
            Text(
                text = "Title :${movie.title}",
                modifier = Modifier.padding(6.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            movie.category?.let {
                Text(
                    text = "Category: $it",
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        })
}