package com.mou.cloudmusic_md3t.ui.screen.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.mou.cloudmusic_md3t.R
import com.mou.cloudmusic_md3t.config.MainNavRoute
import com.mou.cloudmusic_md3t.data.music.EmptySong
import com.mou.cloudmusic_md3t.data.music.PlayableSong
import com.mou.cloudmusic_md3t.data.music.Song
import com.mou.cloudmusic_md3t.ui.components.LoadingProgress
import com.mou.cloudmusic_md3t.ui.screen.main.home.HomeScreen
import com.mou.cloudmusic_md3t.ui.screen.main.me.MeScreen
import com.mou.cloudmusic_md3t.ui.screen.main.playing.PlayingScreen
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavView() {
    val vm: MainNavViewModel = viewModel()
    val navController = rememberNavController()
    val playingStatus = vm.playingStatus.collectAsState()
    val playingSong = vm.playingSong.collectAsState()
    
    Scaffold(
        bottomBar = {
            BottomBar(
                clickCallback = {
                    navController.mainNavTo(it)
                },
                playingStatus = playingStatus,
                playingSong = playingSong
            )
        }
    ){
        NavHost(
            navController = navController,
            startDestination = MainNavRoute.HOME,
            modifier = Modifier.padding(it),
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ){
            composable(MainNavRoute.HOME){
                HomeScreen { song ->
                    vm.insertMusic(song)
                }
            }
            composable(MainNavRoute.PLAYING){
                PlayingScreen()
            }
            composable(MainNavRoute.ME){
                MeScreen()
            }
        }
    }


}

@Composable
fun BottomBar(
    clickCallback: (String) -> Unit,
    playingStatus: State<Boolean>,
    playingSong: State<Song>
              ) {

    Column {
        if(playingStatus.value&&playingSong.value != EmptySong){
            MusicPlayingBar(playingSong = playingSong.value)
        }
        var selectedItem by remember {
            mutableStateOf(MainNavRoute.HOME)
        }
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "") },
                label = { Text(stringResource(id = R.string.home_title)) },
                selected = selectedItem == MainNavRoute.HOME,
                onClick =
                {
                    selectedItem = MainNavRoute.HOME
                    clickCallback(MainNavRoute.HOME)
                },
                alwaysShowLabel = false
            )

            NavigationBarItem(
                selected = selectedItem == MainNavRoute.ME,
                onClick =
                {
                    selectedItem = MainNavRoute.ME
                    clickCallback(MainNavRoute.ME)
                },
                icon = { Icon(Icons.Filled.PlayCircle, contentDescription = "") },
                alwaysShowLabel = false,
                label = { Text(stringResource(id = R.string.me_title)) }
            )
        }
    }
}

@Composable
fun MusicPlayingBar(playingSong: Song) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(playingSong.songCoverImageUri)
                    .crossfade(true)
                    .build()
                    ,
                contentDescription = "Songs Image",
                modifier =
                Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
                        RoundedCornerShape(12.dp)
                    )
            ){
                val state = painter.state
                if(state is AsyncImagePainter.State.Loading){
                    LoadingProgress()
                }else{
                    SubcomposeAsyncImageContent(
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Text(
                text = playingSong.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(0.6f)
            )
            Row {
                IconButton(onClick = { playingSong.switch() }) {
                    // switch lang
                    when(playingSong.status.value){
                        0 -> Icon(Icons.Filled.Downloading, contentDescription = "downloading")
                        1 -> Icon(Icons.Filled.PlayCircle, contentDescription = "playing")
                        2 -> Icon(Icons.Filled.Pause, contentDescription = "pause")
                        3 -> Icon(Icons.Filled.Stop, contentDescription = "stop")
                    }
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.SkipNext, contentDescription = "next")
                }
            }

        }
    }
}

@Preview
@Composable
fun MusicPlayingBarPreview() {
    MusicPlayingBar(playingSong = PlayableSong(1,"https://gitee.com/xiaomouz/xiaomouz/raw/master/upload/images/06bcb167ff840.jpg", "test", 1000))
}

@Preview
@Composable
fun BottomBarPreview(){
    BottomBar(
        clickCallback = {},
        playingStatus = MutableStateFlow(true).collectAsState(),
        playingSong =
            MutableStateFlow(PlayableSong(1,"https://gitee.com/xiaomouz/xiaomouz/raw/master/upload/images/06bcb167ff840.jpg", "test", 1000)).collectAsState()
    )
}

// 跳转到指定路由，避免多次入栈导致无法一次返回
fun NavHostController.mainNavTo(route:String){
    this.navigate(route){
        popUpTo(this@mainNavTo.graph.findStartDestination().id)
        launchSingleTop=true
    }
}