package com.furkankeldal.composetask.presentation.Fragments

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.furkankeldal.composetask.R
import com.furkankeldal.composetask.presentation.Fragments.views.movieListLine
import com.furkankeldal.composetask.presentation.movies.moviesViewModel

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@Composable
fun HomeScreen(modifier: Modifier=Modifier,
    navController: NavController,
    viewModel: moviesViewModel = hiltViewModel(),


) {
    val state = viewModel.state.value

    val ustImages = listOf(
        R.drawable.madame_web,
        R.drawable.ayla,
        R.drawable.thor,
        R.drawable.revenant,
        R.drawable.theglory,
        R.drawable.godfather,
        R.drawable.oppenheimer

    )
    val pagerState = rememberPagerState {
        ustImages.size
    }
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }
    val scope = rememberCoroutineScope()


        Column(
            modifier
                .fillMaxSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = modifier
                    .wrapContentSize()
                    .background(Color.Black)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier
                        .wrapContentSize()
                        .padding(horizontal = 25.dp)

                ) { currentPage ->

                    Card(
                        modifier
                            .height(500.dp)
                            .background(Color.Black)
                            .fillMaxWidth()
                            .padding(vertical = 25.dp, horizontal = 10.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = ustImages[currentPage]),
                            contentDescription = "",
                            modifier
                                .fillMaxSize()
                                .background(Color.Black)
                        )
                    }
                }
                IconButton(
                    onClick = {
                        val nextPage = pagerState.currentPage + 1
                        if (nextPage < ustImages.size) {
                            scope.launch {
                                pagerState.scrollToPage(nextPage)
                            }
                        }
                    },
                    modifier
                        .padding(vertical = 30.dp, horizontal = 15.dp)
                        .size(48.dp)
                        .align(Alignment.CenterEnd)
                        .clip(CircleShape),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(0x52373737)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "",
                        modifier.fillMaxSize(),
                        tint = Color.LightGray
                    )
                }
                IconButton(
                    onClick = {
                        val prevPage = pagerState.currentPage - 1
                        if (prevPage >= 0) {
                            scope.launch {
                                pagerState.scrollToPage(prevPage)
                            }
                        }
                    },
                    modifier
                        .padding(vertical = 30.dp, horizontal = 15.dp)
                        .size(48.dp)
                        .align(Alignment.CenterStart)
                        .clip(CircleShape),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(0x52373737)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "",
                        modifier.fillMaxSize(),
                        tint = Color.LightGray
                    )
                }
            }
            PageIndicator(
                pageCount = ustImages.size,
                currentPage = pagerState.currentPage,
                modifier = modifier
            )





             Box(
                 modifier = Modifier
                     .wrapContentSize()
                     .background(Color.Black)
             ) {

                 Column() {
                     Text(text = "POPULAR", color = Color.White)


                     LazyRow(modifier = Modifier) {
                         items(state.movies) { movie ->
                             movieListLine(movieVertical = movie, onItemClick = {
                                 //navigate to details
                                 navController.navigate(Screens.MovieDetailScreen.screen + "/${movie.imdbID}")
                             })
                         }
                     }
                 }
             }


        }


}



@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount){
            IndicatorDots(isSelected = it == currentPage, modifier= modifier)
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(modifier = modifier
        .padding(2.dp)
        .size(size.value)
        .clip(CircleShape)
        .background(if (isSelected) Color(0xff373737) else Color(0xA8373737))
    )
}


/*

Column(modifier
.fillMaxSize()
.background(Color.Black),
horizontalAlignment = Alignment.CenterHorizontally,

) {
    Box(modifier
        .wrapContentSize(Alignment.Center)
        .background(Color.Black)
    ){
        HorizontalPager(state = pagerState,
            modifier= Modifier
                .wrapContentSize()
                .padding(vertical = 10.dp, horizontal = 25.dp))
        {currentPage->

            Card(
                modifier
                    .height(500.dp)
                    .background(Color.Black)
                    .fillMaxWidth()
                    .padding(vertical = 25.dp, horizontal = 10.dp),
                elevation =CardDefaults.cardElevation(8.dp)
            ) {
                Image(painter = painterResource(id =ustImages[currentPage]), contentDescription ="",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black))


            }
*/
