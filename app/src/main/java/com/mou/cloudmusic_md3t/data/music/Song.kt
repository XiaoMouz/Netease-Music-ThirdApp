package com.mou.cloudmusic_md3t.data.music

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf

object SongStatu{
    const val LOADING = 0
    const val PLAYING = 1
    const val PAUSE = 2
    const val STOP = 3
}

class Song(
    val id: Long,
    val name: String,
    val time: Long,
)
{
    private val _status: MutableState<Int> = mutableIntStateOf(SongStatu.LOADING)
    val status: State<Int> = _status
    fun switch(){
        if (_status.value ==  SongStatu.LOADING)
            return

        if(_status.value == SongStatu.PLAYING)
            _status.value = SongStatu.PAUSE

        if(_status.value == SongStatu.PAUSE || _status.value == SongStatu.STOP)
            _status.value = SongStatu.PLAYING
    }
    fun start(){
        if(_status.value == SongStatu.LOADING)
            _status.value = SongStatu.PLAYING
    }
}