package com.mou.cloudmusic_md3t.ui.screen.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.mou.cloudmusic_md3t.data.music.EmptySong
import com.mou.cloudmusic_md3t.data.music.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainNavViewModel:ViewModel() {
    // Songs list
    private val _snapPlayList: MutableStateFlow<MutableList<Song>> =
        MutableStateFlow(MutableList<Song>(0) { EmptySong })
    val snapPlayList:StateFlow<List<Song>> = _snapPlayList

    // Songs -> todo: auto get from snap playlist
    // issue: java.lang.reflect.InvocationTargetException
    private val _playingSong:MutableStateFlow<Song> = MutableStateFlow(EmptySong)
    val playingSong:StateFlow<Song> = _playingSong

    // Playing status -> todo: auto get from playing song
    private val _playingStatus: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val playingStatus: StateFlow<Boolean> = _playingStatus



    fun insertMusic(song: Song){
        if(_snapPlayList.value.contains(song))
            return
        if(_snapPlayList.value.isEmpty())
            _snapPlayList.value.add(song)
        if (_snapPlayList.value[0]==EmptySong)
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
