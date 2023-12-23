package com.mou.cloudmusic_md3t.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.mou.cloudmusic_md3t.R
import com.mou.cloudmusic_md3t.config.MainNavRoute
import com.mou.cloudmusic_md3t.data.entities.EmptySong
import com.mou.cloudmusic_md3t.data.entities.PlayableSong
import com.mou.cloudmusic_md3t.data.entities.Song
import com.mou.cloudmusic_md3t.ui.screen.main.mainNavTo
import kotlinx.coroutines.flow.MutableStateFlow

class BottomBarViewModel {
    private val _navBottomState = MutableStateFlow(true)
    val navBottomState = _navBottomState

    fun changeNavBottomState(state: Boolean) {
        _navBottomState.apply {
            value = state
        }
    }
}

@Composable
fun BottomBar(
    clickCallback: (String) -> Unit,
    playingStatus: State<Boolean>,
    playingSong: State<Song>
) {
    // 导航栏图标与文字绑定
    val navList = listOf<Pair<Int,ImageVector>>(
        Pair(R.string.home_title,Icons.Filled.Home),
        Pair(R.string.me_title, Icons.Filled.AccountCircle),
    )
    // 当前选中的导航栏索引
    var nowActiveIndex by remember {
        mutableIntStateOf(0)
    }

    val mainNavController = rememberNavController()

    // 监听 Navigation 变化，更新样式
    mainNavController.addOnDestinationChangedListener{
            _, destination, _ ->
        MainNavRoute.apply {
            when(destination.route){
                HOME ->{
                    nowActiveIndex = 0
                }
                ME ->{
                    nowActiveIndex = 1
                }
            }
        }
    }

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
            // 导航栏条目，遍历 navList 塞入对应 item
            navList.forEachIndexed { index, pair ->
                NavigationBarItem(
                    selected = nowActiveIndex == index,
                    onClick = {
                        // 对应的点击操作
                        nowActiveIndex = when (index) {
                            0 -> {
                                mainNavController.mainNavTo(MainNavRoute.HOME)
                                index
                            }

                            1 -> {
                                mainNavController.mainNavTo(MainNavRoute.ME)
                                index
                            }

                            else -> {
                                mainNavController.mainNavTo(MainNavRoute.HOME)
                                index
                            }
                        }
                    },
                    icon = {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            imageVector = pair.second,
                            contentDescription = stringResource(id = pair.first),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    label = {
                        Text(text = stringResource(id = pair.first))
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedTextColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
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