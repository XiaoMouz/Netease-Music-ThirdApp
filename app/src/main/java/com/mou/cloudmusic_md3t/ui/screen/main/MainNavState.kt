package com.mou.cloudmusic_md3t.ui.screen.main

import androidx.compose.ui.graphics.vector.ImageVector
import com.mou.cloudmusic_md3t.data.entities.EmptySong
import com.mou.cloudmusic_md3t.data.entities.Song

data class BottomBarState(
    val navList: List<Pair<Int, ImageVector>>,
    val nowActiveIndex: Int = 0,
)

data class MusicBarState(
    val isPlaying: Boolean = false,
    val song: Song = EmptySong
)