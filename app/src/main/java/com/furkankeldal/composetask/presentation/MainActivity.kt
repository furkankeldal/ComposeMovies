package com.furkankeldal.composetask.presentation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RestrictTo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
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
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<mainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
            setOnExitAnimationListener{ screen->
                val zoomX=ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                zoomX.interpolator=OvershootInterpolator()
                zoomX.duration=500L
                zoomX.doOnEnd { screen.remove() }

                val zoomY=ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                zoomY.interpolator=OvershootInterpolator()
                zoomY.duration=500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }
        setContent {
            ComposeTaskTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppBars()
                }
            }
        }
    }
}
data class DrawerItem(
    val title:String,
    val selectedIcon:ImageVector,
    val unSelectedIcon:ImageVector,
    val navigation:String,
    val bottomSelected:ImageVector

)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBars(){
    val navigationController= rememberNavController()
    val context = LocalContext.current.applicationContext
    val selected= remember {
        mutableStateOf(Icons.Default.Home)
    }
    val scrollBehavior=TopAppBarDefaults.enterAlwaysScrollBehavior()
    val DrawerState= rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val scope= rememberCoroutineScope()
    val items= listOf(
        DrawerItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            navigation = Screens.HomeScreen.screen,
            bottomSelected = Icons.Default.Home
        ),
        DrawerItem(
            title = "Search",
            selectedIcon = Icons.Filled.Search,
            unSelectedIcon = Icons.Outlined.Search,
            navigation = Screens.SearchScreen.screen,
            bottomSelected = Icons.Default.Search
        ),
        DrawerItem(
            title = "Favorites",
            selectedIcon = Icons.Filled.Favorite,
            unSelectedIcon = Icons.Outlined.FavoriteBorder,
            navigation = Screens.FavoritesScreen.screen,
            bottomSelected = Icons.Default.Favorite
        ),
        DrawerItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unSelectedIcon = Icons.Outlined.Settings,
            navigation = Screens.SettingsScreen.screen,
            bottomSelected = Icons.Default.Settings
        )

    )
    ModalNavigationDrawer(

        drawerContent = {
        ModalDrawerSheet(
            drawerContainerColor = Color.Black
        ) {
            items.forEachIndexed { index, item ->
                NavigationDrawerItem(label = { Text(text = item.title) },
                    selected = index==selectedIndex,
                    onClick = {
                        selected.value=item.bottomSelected
                        navigationController.navigate(item.navigation)
                        selectedIndex=index
                        scope.launch {
                            DrawerState.close()
                        }

                    },
                    icon={
                        Icon(imageVector = if(index==selectedIndex){
                            item.selectedIcon
                        }else item.unSelectedIcon,
                            contentDescription =item.title )
                    },
                    modifier = Modifier.padding(5.dp),
                    colors = NavigationDrawerItemDefaults.colors(Color.Red)
                )

            }
        }
    },
        drawerState = DrawerState
        ) {
         
        Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(Color.Black),
                    title = {
                        Text(color = Color.White, text = "Nefflix",
                            modifier = Modifier.clickable(
                                onClick = {
                                    selected.value = Icons.Default.Home
                                    navigationController.navigate(Screens.HomeScreen.screen)
                                }
                            )

                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                DrawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                            )

                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            selected.value = Icons.Default.Favorite
                            navigationController.navigate(Screens.FavoritesScreen.screen)
                            selectedIndex=2
                        }) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "favorites",
                                tint = if (selected.value == Icons.Default.Favorite) Color.Red else Color.White
                            )

                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "profile",
                                tint = Color.White
                            )

                        }
                    },
                   scrollBehavior = scrollBehavior
                )
            },
            bottomBar = {
                BottomAppBar(
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
                             selectedIndex=0
                            },
                        ) {
                            Icon(
                                Icons.Default.Home,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp),
                                tint = if (selected.value == Icons.Default.Home) Color.Red else Color.White
                            )
                        }
                        Text(
                            text = "Home", modifier = Modifier.padding(vertical = 30.dp),
                            if (selected.value == Icons.Default.Home) Color.Red else Color.White
                        )

                    }
                    Box(modifier = Modifier.padding(horizontal = 20.dp)) {


                        IconButton(
                            onClick = {
                                selected.value = Icons.Default.Search
                                navigationController.navigate(Screens.SearchScreen.screen) {
                                    popUpTo(0)
                                }
                                selectedIndex=1

                            },
                        ) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp),
                                tint = if (selected.value == Icons.Default.Search) Color.Red else Color.White
                            )

                        }
                        Text(
                            text = "Search", modifier = Modifier.padding(vertical = 30.dp),
                            if (selected.value == Icons.Default.Search) Color.Red else Color.White
                        )

                    }
                    Box(modifier = Modifier.padding(horizontal = 20.dp)) {


                        IconButton(
                            onClick = {
                                selected.value = Icons.Default.Favorite
                                navigationController.navigate(Screens.FavoritesScreen.screen) {
                                    popUpTo(0)
                                }
                                selectedIndex=2

                            },
                        ) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp),
                                tint = if (selected.value == Icons.Default.Favorite) Color.Red else Color.White
                            )

                        }
                        Text(
                            text = "Favorites", modifier = Modifier.padding(vertical = 30.dp),
                            if (selected.value == Icons.Default.Favorite) Color.Red else Color.White
                        )
                    }
                    Box(modifier = Modifier.padding(horizontal = 20.dp)) {


                        IconButton(
                            onClick = {
                                selected.value = Icons.Default.Settings
                                navigationController.navigate(Screens.SettingsScreen.screen) {
                                    popUpTo(0)
                                }
                                selectedIndex=3
                            },
                        ) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                                /* .clickable (
                                    onClick = {selected.value=Icons.Default.Settings
                                     navigationController.navigate((Screens.SettingsScreen.screen))         },
                                    indication = null,
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }
                        )*/,
                                tint = if (selected.value == Icons.Default.Settings) Color.Red else Color.White
                            )

                        }
                        Text(
                            text = "Settings", modifier = Modifier.padding(vertical = 30.dp),
                            if (selected.value == Icons.Default.Settings) Color.Red else Color.White
                        )
                    }

                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navigationController,
                startDestination = Screens.HomeScreen.screen,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Screens.HomeScreen.screen) { HomeScreen(navController = navigationController) }
                composable(Screens.SearchScreen.screen) { SearchScreen(navController = navigationController) }
                composable(Screens.FavoritesScreen.screen) { FavoritesScreen() }
                composable(Screens.SettingsScreen.screen) { SettingsScreen() }
                composable(Screens.MovieDetailScreen.screen + "/{${IMDB_ID}}") {
                    movieDetailScreen()
                }

            }


        }
    }
}






