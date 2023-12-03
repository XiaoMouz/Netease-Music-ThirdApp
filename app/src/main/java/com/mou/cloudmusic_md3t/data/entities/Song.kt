package com.mou.cloudmusic_md3t.data.entities

interface Song {
    val id: Long
    val songCoverImageUri: String
    val name: String
    val time: Long
    val status: SongStatus

    fun switch()
    fun start()
}

enum class SongStatus {
    LOADING,
    PLAYING,
    PAUSE,
    STOP,
    PENDING
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
    override val status: SongStatus
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
    private var _status: SongStatus = SongStatus.LOADING
    override val status: SongStatus = _status

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
