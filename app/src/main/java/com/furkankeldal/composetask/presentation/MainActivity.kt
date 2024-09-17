package com.furkankeldal.composetask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.furkankeldal.composetask.presentation.Fragments.FavoritesScreen
import com.furkankeldal.composetask.presentation.Fragments.HomeScreen
import com.furkankeldal.composetask.presentation.Fragments.Screens
import com.furkankeldal.composetask.presentation.Fragments.SettingsScreen

import com.furkankeldal.composetask.presentation.movie_detail.views.movieDetailScreen
import com.furkankeldal.composetask.presentation.Fragments.SearchScreen
import com.furkankeldal.composetask.ui.theme.ComposeTaskTheme
import com.furkankeldal.composetask.util.Constants.IMDB_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTaskTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    BottomAppBar()
                }
            }
        }
    }
}

@Composable
fun BottomAppBar(){
    val navigationController= rememberNavController()
    val context = LocalContext.current.applicationContext
    val selected= remember {
        mutableStateOf(Icons.Default.Home)
    }
    Scaffold(
        bottomBar = {
            androidx.compose.material3.BottomAppBar(
                containerColor = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)

            ) {
                Box(modifier = Modifier.padding(horizontal = 24.dp)) {


                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.Home
                            navigationController.navigate(Screens.HomeScreen.screen) {
                                popUpTo(0)
                            }

                        },
                       ) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = null,
                            modifier = Modifier.size(25.dp),
                            tint = if (selected.value == Icons.Default.Home) Color.Green else Color.White
                        )
                    }
                   Text(text = "Home", modifier = Modifier.padding(vertical = 30.dp),
                       if (selected.value==Icons.Default.Home)Color.Green else Color.White)

                }
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {


                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.Search
                            navigationController.navigate(Screens.SearchScreen.screen) {
                                popUpTo(0)
                            }

                        },
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.size(25.dp),
                            tint = if (selected.value == Icons.Default.Search) Color.Green else Color.White
                        )

                    }
                    Text(text = "Search", modifier = Modifier.padding(vertical = 30.dp),
                        if (selected.value==Icons.Default.Search)Color.Green else Color.White)

                }
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {


                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.Favorite
                            navigationController.navigate(Screens.FavoritesScreen.screen) {
                                popUpTo(0)
                            }

                        },
                    ) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(25.dp),
                            tint = if (selected.value == Icons.Default.Favorite) Color.Green else Color.White
                        )

                    }
                    Text(text = "Favorites", modifier = Modifier.padding(vertical = 30.dp),
                        if (selected.value==Icons.Default.Favorite)Color.Green else Color.White)
                }
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {


                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.Settings
                            navigationController.navigate(Screens.SettingsScreen.screen) {
                                popUpTo(0)
                            }

                        },
                    ) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = null,
                            modifier = Modifier.size(25.dp),
                            tint = if (selected.value == Icons.Default.Settings) Color.Green else Color.White
                        )

                    }
                    Text(text = "Settings", modifier = Modifier.padding(vertical = 30.dp),
                        if (selected.value==Icons.Default.Settings)Color.Green else Color.White)
                }

            }
        }
    ) {  paddingValues ->
        NavHost(navController = navigationController,
            startDestination =Screens.HomeScreen.screen,
            modifier = Modifier.padding(paddingValues)) {
            composable(Screens.HomeScreen.screen){ HomeScreen(navController = navigationController) }
            composable(Screens.SearchScreen.screen){ SearchScreen(navController = navigationController) }
            composable(Screens.FavoritesScreen.screen){ FavoritesScreen() }
            composable(Screens.SettingsScreen.screen){ SettingsScreen()}
            composable( Screens.MovieDetailScreen.screen + "/{${IMDB_ID}}") {
                movieDetailScreen()
            }

        }


    }
}




