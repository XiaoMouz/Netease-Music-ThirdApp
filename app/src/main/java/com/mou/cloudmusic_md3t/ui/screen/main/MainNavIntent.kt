package com.mou.cloudmusic_md3t.ui.screen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.lifecycle.ViewModel
import com.mou.cloudmusic_md3t.R
import com.mou.cloudmusic_md3t.data.entities.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// todo: MVI
sealed class MainNavIntent {
    data class ChangeNavBottomIndex(val index: Int) : MainNavIntent()
    data class ChangeMusicState(val show: Boolean, val song: Song): MainNavIntent()
}





class MainNavViewModel : ViewModel() {
    val navList = listOf(
        Pair(R.string.home_title, Icons.Filled.Home),
        Pair(R.string.me_title, Icons.Filled.AccountCircle),
    )

    // todo: MVI
    private val _bottomBarState = MutableStateFlow(BottomBarState(navList))
    val bottomBarState: StateFlow<BottomBarState> = _bottomBarState
    private val _musicBarState = MutableStateFlow(MusicBarState())
    val musicBarState: StateFlow<MusicBarState> = _musicBarState

    fun intentHandler(intent: MainNavIntent){
        when(intent){
            is MainNavIntent.ChangeNavBottomIndex -> _bottomBarState.apply {
                value = value.copy(nowActiveIndex = intent.index)
            }
            is MainNavIntent.ChangeMusicState -> _musicBarState.apply{
                value = value.copy(isPlaying = intent.show ,song = intent.song)
            }
        }
    }

}
