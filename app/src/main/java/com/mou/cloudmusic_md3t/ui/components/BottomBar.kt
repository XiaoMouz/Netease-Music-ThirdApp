package com.mou.cloudmusic_md3t.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mou.cloudmusic_md3t.ui.screen.main.BottomBarState
import com.mou.cloudmusic_md3t.ui.screen.main.MusicBarState


//class BottomBarViewModel {
//    private val _navBottomState = MutableStateFlow(true)
//    val navBottomState = _navBottomState
//
//    fun changeNavBottomState(state: Boolean) {
//        _navBottomState.apply {
//            value = state
//        }
//    }
//}

@Composable
fun BottomBar(
    bottomBarState: BottomBarState,
    musicBarState: MusicBarState,
    changeNavBottomIndex:(index:Int)->Unit,

) {
    // 当前选中的导航栏索引
    val nowActiveIndex = bottomBarState.nowActiveIndex


//    // 监听 Navigation 变化，更新样式
//    mainNavController.addOnDestinationChangedListener{
//            _, destination, _ ->
//        MainNavRoute.apply {
//            when(destination.route){
//                HOME ->{
//                    nowActiveIndex = 0
//                }
//                ME ->{
//                    nowActiveIndex = 1
//                }
//            }
//        }
//    }

    Column {
        val density = LocalDensity.current
        AnimatedVisibility(
            visible = musicBarState.isPlaying,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { 80.dp.roundToPx() }
            } + fadeIn(
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            MusicPlayingBar(musicBarState)
        }

        NavigationBar {
            // 导航栏条目，遍历 navList 塞入对应 item
            bottomBarState.navList.forEachIndexed { index, pair ->
                NavigationBarItem(
                    selected = nowActiveIndex == index,
                    onClick = {
                        changeNavBottomIndex(index)
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

//@Preview
//@Composable
//fun BottomBarPreview() {
//    BottomBar(
//        clickCallback = {},
//        playingStatus = MutableStateFlow(true).collectAsState(),
//        playingSong =
//        MutableStateFlow(
//            PlayableSong(
//                1,
//                "https://gitee.com/xiaomouz/xiaomouz/raw/master/upload/images/06bcb167ff840.jpg",
//                "test",
//                1000
//            )
//        ).collectAsState(),
//        rememberNavController()
//    )
//}