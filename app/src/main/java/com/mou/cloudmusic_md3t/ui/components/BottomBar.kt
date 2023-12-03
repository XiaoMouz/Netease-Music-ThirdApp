package com.mou.cloudmusic_md3t.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mou.cloudmusic_md3t.R
import com.mou.cloudmusic_md3t.config.MainNavRoute
import com.mou.cloudmusic_md3t.data.entities.EmptySong
import com.mou.cloudmusic_md3t.data.entities.PlayableSong
import com.mou.cloudmusic_md3t.data.entities.Song
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun BottomBar(
    clickCallback: (String) -> Unit,
    playingStatus: State<Boolean>,
    playingSong: State<Song>
) {

    Column {
        val density = LocalDensity.current
        AnimatedVisibility(
            visible = playingStatus.value && playingSong.value != EmptySong,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { 80.dp.roundToPx() }
            } + fadeIn(
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
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

@Preview
@Composable
fun BottomBarPreview() {
    BottomBar(
        clickCallback = {},
        playingStatus = MutableStateFlow(true).collectAsState(),
        playingSong =
        MutableStateFlow(
            PlayableSong(
                1,
                "https://gitee.com/xiaomouz/xiaomouz/raw/master/upload/images/06bcb167ff840.jpg",
                "test",
                1000
            )
        ).collectAsState()
    )
}