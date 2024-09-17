package com.furkankeldal.composetask.presentation.movies

sealed class moviesEvent {
    data class Search(val searchString :String) : moviesEvent()

}