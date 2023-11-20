package com.mou.cloudmusic_md3t.data.music

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf

interface Song {
    val id: Long
    val songCoverImageUri: String
    val name: String
    val time: Long
    val status: Int

    fun switch()
    fun start()
}

object SongStatus {
    const val LOADING = 0
    const val PLAYING = 1
    const val PAUSE = 2
    const val STOP = 3
    const val PENDING = 4
}

object EmptySong : Song {
    override val id: Long
        get() = -1
    override val songCoverImageUri: String
        get() = ""
    override val name: String
        get() = ""
    override val time: Long
        get() = 0
    override val status: Int
        get() = SongStatus.STOP

    override fun switch() {
    }

    override fun start() {
    }
}

data class PlayableSong(
    override val id: Long,
    override val songCoverImageUri: String,
    override val name: String,
    override val time: Long,

    ) : Song {
    private var _status: Int = SongStatus.LOADING
    override val status: Int = _status

    // todo: loading logic
    override fun switch() {
        if (_status == SongStatus.LOADING)
            return

        if (_status == SongStatus.PLAYING)
            _status = SongStatus.PAUSE

        if (_status == SongStatus.PAUSE || _status == SongStatus.STOP)
        // todo: loading logic
            _status = SongStatus.PLAYING
    }

    override fun start() {
        if (_status == SongStatus.PENDING || _status == SongStatus.STOP)
        // todo: cache found & loading logic
            _status = SongStatus.PLAYING
    }
}
