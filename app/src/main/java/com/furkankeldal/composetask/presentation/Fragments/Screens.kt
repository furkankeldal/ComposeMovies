package com.furkankeldal.composetask.presentation.Fragments

sealed class Screens (val screen:String) {
   data object SearchScreen : Screens("search_screen")
   data object MovieDetailScreen : Screens("movie_detail_screen")
   data object HomeScreen: Screens("home_screen")
   data object SettingsScreen: Screens("settings_screen")
   data object FavoritesScreen : Screens("favorites_screen")


}
