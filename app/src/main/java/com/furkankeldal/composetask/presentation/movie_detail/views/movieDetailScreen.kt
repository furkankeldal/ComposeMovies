package com.furkankeldal.composetask.presentation.movie_detail.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.furkankeldal.composetask.presentation.Fragments.FavoritesScreen
import com.furkankeldal.composetask.presentation.Fragments.HomeScreen
import com.furkankeldal.composetask.presentation.Fragments.Screens
import com.furkankeldal.composetask.presentation.Fragments.SearchScreen
import com.furkankeldal.composetask.presentation.Fragments.SettingsScreen
import com.furkankeldal.composetask.presentation.movie_detail.movieDetailViewModel
import com.furkankeldal.composetask.ui.theme.ComposeTaskTheme
import com.furkankeldal.composetask.util.Constants.IMDB_ID


@Composable
fun movieDetailScreen (
    movieDetailViewModel: movieDetailViewModel = hiltViewModel()

) {
    val ScrollState = rememberScrollState()
    val state = movieDetailViewModel.state.value


        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(ScrollState)
                .background(Color.Black), contentAlignment = Center
        ) {
            state.movie?.let {

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = rememberImagePainter(data = it.Poster),
                        contentDescription = it.Title,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(300.dp, 300.dp)
                            .clip(RectangleShape)
                            .align(CenterHorizontally)
                    )

                    Text(
                        text = it.Title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(14.dp),
                        color = Color.White
                    )

                    Text(
                        text = it.Year,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(14.dp),
                        color = Color.White
                    )

                    Text(
                        text = it.Actors,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(14.dp),
                        color = Color.White
                    )

                    Text(
                        text = it.Country,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(14.dp),
                        color = Color.White
                    )

                    Text(
                        text = "Director: ${it.Director}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(14.dp),
                        color = Color.White
                    )

                    Text(
                        text = "IMDB Rating: ${it.imdbRating}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(14.dp),
                        color = Color.White
                    )

                }
            }

            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                        .align(Alignment.Center)
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

    }
