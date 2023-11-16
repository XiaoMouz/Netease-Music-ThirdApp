package com.mou.cloudmusic_md3t.ui.screen.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.Square
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mou.cloudmusic_md3t.R
import com.mou.cloudmusic_md3t.config.MainNavRoute
import com.mou.cloudmusic_md3t.data.music.Song
import com.mou.cloudmusic_md3t.ui.screen.main.home.HomeScreen
import com.mou.cloudmusic_md3t.ui.screen.main.me.MeScreen
import com.mou.cloudmusic_md3t.ui.screen.main.playing.PlayingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavView() {
    val navController = rememberNavController()
    Scaffold(){
        NavHost(navController = navController, startDestination = MainNavRoute.HOME, modifier = Modifier.padding(it)){
            composable(MainNavRoute.HOME){
                HomeScreen()
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
fun BottomBar(clickCallback: (String) -> Unit, playingStatus: Boolean) {

    Column {
        if(true){
            MusicPlayingBar(playingSong = Song(1, "test", 1000))
        }
        var selectedItem = MainNavRoute.HOME
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
    Surface(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_20230108_043812_841),
                contentDescription = "",
                modifier =
                Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .border(
                        BorderStroke(1.dp, MaterialTheme.colorScheme.secondaryContainer),
                        RoundedCornerShape(6.dp)
                    )
                ,

            )
            Text(
                text = playingSong.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(0.6f)
            )
            Row {
                TextButton(onClick = { playingSong.switch() }) {
                    // swtich lang
                    when(playingSong.status.value){
                        0 -> Icon(Icons.Filled.Downloading, contentDescription = "downloading")
                        1 -> Icon(Icons.Filled.PlayCircle, contentDescription = "playing")
                        2 -> Icon(Icons.Filled.Pause, contentDescription = "pause")
                        3 -> Icon(Icons.Filled.Stop, contentDescription = "stop")
                    }
                }
                TextButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.SkipNext, contentDescription = "next")
                }
            }

        }
    }
}

@Preview
@Composable
fun MusicPlayingBarPreview() {
    MusicPlayingBar(playingSong = Song(1, "test", 1000))
}

@Preview
@Composable
fun BottomBarPreview(){
    BottomBar(clickCallback = {}, playingStatus = false)
}
