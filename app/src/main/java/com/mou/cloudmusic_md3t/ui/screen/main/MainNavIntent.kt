package com.mou.cloudmusic_md3t.ui.screen.main

import androidx.lifecycle.ViewModel
import com.mou.cloudmusic_md3t.data.entities.EmptySong
import com.mou.cloudmusic_md3t.data.entities.Song
import com.mou.cloudmusic_md3t.data.entities.SongStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// todo: MVI
sealed class MainNavIntent {
    data class MusicBarState(
        val isShow: Boolean = false,
        val isPlaying: Boolean = false,
        val nowPlayingState: SongStatus = SongStatus.STOP,
    ) : MainNavIntent()
}





class MainNavViewModel : ViewModel() {
    // todo: MVI
    private val _uiState: MutableStateFlow<MainNavIntent> =
        MutableStateFlow(MainNavIntent.MusicBarState())
    val uiState: StateFlow<MainNavIntent> = _uiState

    // Songs list
    private val _snapPlayList: MutableStateFlow<MutableList<Song>> =
        MutableStateFlow(MutableList<Song>(0) { EmptySong })
    val snapPlayList: StateFlow<List<Song>> = _snapPlayList

    // Songs -> todo: auto get from snap playlist
    // issue: java.lang.reflect.InvocationTargetException
    private val _playingSong: MutableStateFlow<Song> = MutableStateFlow(EmptySong)
    val playingSong: StateFlow<Song> = _playingSong

    // Playing status -> todo: auto get from playing song
    private val _playingStatus: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val playingStatus: StateFlow<Boolean> = _playingStatus


    fun insertMusic(song: Song) {
        if (_snapPlayList.value.contains(song))
            return
        if (_snapPlayList.value.isEmpty())
            _snapPlayList.value.add(song)
        if (_snapPlayList.value[0] == EmptySong)
        // insert to 0
            _snapPlayList.value.add(0, song)
        else
            _snapPlayList.value.add(_snapPlayList.value.size - 1, song)

        // todo: delete after test
        _playingSong.value = song
        _playingSong.value.start()
        _playingStatus.value = true

    }
}
